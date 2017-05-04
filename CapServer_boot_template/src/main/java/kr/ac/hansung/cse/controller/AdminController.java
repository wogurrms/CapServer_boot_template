package kr.ac.hansung.cse.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import kr.ac.hansung.cse.model.Product;
import kr.ac.hansung.cse.service.ProductService;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private ProductService productService;

	@RequestMapping
	public String adminPage() {
		return "admin";
	}

	@RequestMapping("/productInventory")
	public String getProducts(Model model) {
		List<Product> products = productService.getProducts();
		model.addAttribute("products", products);
		return "productInventory";
	}

	@RequestMapping(value = "/productInventory/addProduct", method = RequestMethod.GET)
	public String addProduct(Model model) {
		Product product = new Product();
		product.setCategory("컴퓨터");
		model.addAttribute("product", product);
		return "addProduct";
	}

	@RequestMapping(value = "/productInventory/addProduct", method = RequestMethod.POST)
	public String addProductPost(@Valid Product product, BindingResult result, HttpServletRequest request) {
		// @Valid 에 넘어온 객체가 검증이 되고 결과가 BindingResult 에 자동으로 들어가게 된다.
		// View 로 Product 와 BindingResult 가 둘다 넘어가서 검증이 틀릴 경우 기존입력 값은 지워지지 않고 form 에 유지된다.
		// 사용자가 입력한 form data 가 Product 객체에 data binding 되서 들어온다.
		
		if(result.hasErrors()){
			System.out.println("=========Form data has some Errors========");
			List<ObjectError> errors = result.getAllErrors();
			for(ObjectError error : errors){
				System.out.println(error.getDefaultMessage());
			}
			
			return "addProduct";
		}
		
		
		// ---------- ImageFile 저장하는 과정------------
		MultipartFile productImage = product.getProductImage();
		// request 를 요청한 곳의 root directory 를 가져온다.
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");
		Path savePath = Paths.get(rootDirectory+"\\resources\\images\\"+productImage.getOriginalFilename());
		if(productImage != null && !productImage.isEmpty()){
			try{
				// 파일을 저장할때는 transforTo 메소드 사용
				productImage.transferTo(new File(savePath.toString()));
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		// ------------------------------------------
		
		product.setImageFilename(productImage.getOriginalFilename());		
		
		if (!productService.addProduct(product)) {
			System.out.println("Adding Product Cannot be done");
		}
		// 바로 productInventory 를 호출하면 db에서 가져오지 않으므로 다시 redirect 하여 db 에서 다 가져오도록 한다.
		return "redirect:/admin/productInventory";
	}

	@RequestMapping("productInventory/deleteProduct/{id}")
	public String deleteProduct(@PathVariable int id, HttpServletRequest request) {

		// ---------- ImageFile 저장하는 과정------------
		
		Product product = productService.getProductById(id);
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");
		Path path = Paths.get(rootDirectory+"\\resources\\images\\"+product.getImageFilename());
		// File 이 존재하는지 검사
		if(Files.exists(path)){
			try{
				// File 이 존재하면 삭제
				Files.delete(path);
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		// ------------------------------------------	
		
		
		// {id} 값이 @PathVariable로 맵핑 되게 된다.
		if (!productService.deleteProductById(id)) {
			System.out.println("Deleting Product Cannot be done");
		}
		return "redirect:/admin/productInventory";
	}

	@RequestMapping(value = "productInventory/editProduct/{id}", method = RequestMethod.GET)
	public String editProduct(@PathVariable int id, Model model) { 
		// {id} 값이 @PathVariable 로 맵핑 되게된다.

		Product product = productService.getProductById(id);

		model.addAttribute("product", product);

		return "editProduct";
	}

	@RequestMapping(value = "productInventory/editProduct", method = RequestMethod.POST)
	public String editProductPost(@Valid Product product, BindingResult result, HttpServletRequest request) { 
		// View 로 Product 와 BindingResult 가 둘다 넘어가서 검증이 틀릴 경우 기존입력 값은 지워지지 않고 form 에 유지된다.
		
		if(result.hasErrors()){
			System.out.println("=========Form data has some Errors========");
			List<ObjectError> errors = result.getAllErrors();
			for(ObjectError error : errors){
				System.out.println(error.getDefaultMessage());
			}
			
			return "editProduct";
		}
		

		// ---------- ImageFile 저장하는 과정------------
		MultipartFile productImage = product.getProductImage();
		// request 를 요청한 곳의 root directory 를 가져온다.
		String rootDirectory = request.getSession().getServletContext().getRealPath("/");
		Path savePath = Paths.get(rootDirectory+"\\resources\\images\\"+productImage.getOriginalFilename());
		if(productImage != null && !productImage.isEmpty()){
			try{
				// 파일을 저장할때는 transforTo 메소드 사용
				productImage.transferTo(new File(savePath.toString()));
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		// ------------------------------------------
		
		product.setImageFilename(productImage.getOriginalFilename());	
		
		
		if (!productService.editProduct(product)) {
			System.out.println("Editing Product Cannot be done");
		}

		return "redirect:/admin/productInventory";
	}

}
