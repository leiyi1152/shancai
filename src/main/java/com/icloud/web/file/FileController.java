package com.icloud.web.file;

import java.util.UUID;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.icloud.common.Contants;
import com.icloud.common.ftp.FtpFileService;
import com.icloud.common.util.ConfigUtil;
import com.icloud.web.AppBaseController;

@RestController
public class FileController extends AppBaseController {
	@Autowired
	private FtpFileService ftpFileService;
	
	@RequestMapping("/fileUpload")
	public String fileUpFtp(@RequestParam("file") MultipartFile file,HttpServletRequest request){
		JSONObject resultObj = new JSONObject();
		if(null!=file){
			String fileName = file.getOriginalFilename();
			String extension = fileName.lastIndexOf(".") == -1 ? "" : fileName
					.substring(fileName.lastIndexOf(".") + 1);
			fileName = UUID.randomUUID().toString().replace("-", "")+"."+extension;
			String basePath = Contants.IMG_BASE_PATH_;
			try {
				boolean result = false;
				synchronized (this) {
					result = ftpFileService.upload(file.getBytes(), ConfigUtil.get("filepath")+basePath, fileName);
				}
				if(!result){
			    	resultObj.put("errCode", "4001");
					resultObj.put("resultMsg", "服务器未知错误");
					return pakageJsonp(resultObj);
			    }
			} catch (Exception e) {
				e.printStackTrace();
				
				resultObj.put("errCode", "4001");
				resultObj.put("resultMsg", "服务器未知错误");
				return pakageJsonp(resultObj);
			}finally {
				
			}
			JSONObject resultDate = new JSONObject();
			resultDate.put("imgUrl", Contants._DO_MAIN_+basePath+fileName);
			resultObj.put("resultData", resultDate);
			resultObj.put("errCode", "0000");
			resultObj.put("resultMsg", "图片上传成功");
			return pakageJsonp(resultObj);
		}else{
			resultObj.put("errCode", "4000");
			resultObj.put("resultMsg", "图片为空");
			return pakageJsonp(resultObj);
		}
   }

	
	/**后台 **/
	@RequestMapping("/upload")
	public String handleFileUpload(@RequestParam("file") MultipartFile file,HttpServletRequest request) {
		JSONObject resultObj = new JSONObject();
		if(null!=file){
			if(file.getSize()>1024*1024){
				resultObj.put("code", "4001");
				resultObj.put("msg", "图片大小不能超过1M，请压缩后上传");
				return pakageJsonp(resultObj);
			}
			
			String fileName = file.getOriginalFilename();
			fileName = UUID.randomUUID().toString().replace("-", "")+"_"+fileName;
			String basePath = Contants.IMG_BASE_PATH_;
			try {
				boolean result = false;
				synchronized (this) {
					result = ftpFileService.upload(file.getBytes(), ConfigUtil.get("filepath")+basePath, fileName);
				}
				if(!result){
			    	resultObj.put("code", "4001");
					resultObj.put("msg", "服务器未知错误");
					return pakageJsonp(resultObj);
			    }
			} catch (Exception e) {
				e.printStackTrace();
				
				resultObj.put("code", "4001");
				resultObj.put("msg", "服务器未知错误");
				return pakageJsonp(resultObj);
			}finally {
				
			}
			JSONObject resultDate = new JSONObject();
			resultDate.put("src", Contants._DO_MAIN_+basePath+fileName);
			resultObj.put("data", resultDate);
			resultObj.put("code", 0);
			resultObj.put("msg", "图片上传成功");
			return pakageJsonp(resultObj);
		}else{
			resultObj.put("code", "4000");
			resultObj.put("msg", "图片为空");
			return pakageJsonp(resultObj);
		}
	}
	
	
	
	
}
