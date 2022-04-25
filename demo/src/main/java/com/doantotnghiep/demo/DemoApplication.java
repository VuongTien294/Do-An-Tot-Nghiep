package com.doantotnghiep.demo;

import com.cloudinary.Cloudinary;
import com.cloudinary.SingletonManager;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class DemoApplication {
	public static void main(String[] args) {

		// Set Cloudinary instance
//		Cloudinary cloudinary = new Cloudinary(ObjectUtils.asMap(
//				"cloud_name", "xxx", // insert here you cloud name
//				"api_key", "xxx", // insert here your api code
//				"api_secret", "xxx")); // insert here your api secret
//		SingletonManager manager = new SingletonManager();
//		manager.setCloudinary(cloudinary);
//		manager.init();


		SpringApplication.run(DemoApplication.class, args);
	}
}
