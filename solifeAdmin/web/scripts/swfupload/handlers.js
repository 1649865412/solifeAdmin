/* Demo Note:  This demo uses a FileProgress class that handles the UI for displaying the file name and percent complete.
The FileProgress class is not part of SWFUpload.
*/


/* **********************
   Event Handlers
   These are my custom event handlers to make my
   web application behave the way I went when SWFUpload
   completes different tasks.  These aren't part of the SWFUpload
   package.  They are part of my application.  Without these none
   of the actions SWFUpload makes will show up in my application.
   ********************** */
function swfu_fileQueueError(file, errorCode, message) {
	try {
		if (errorCode === SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED) {
			alert("You have attempted to queue too many files.\n" + (message === 0 ? "You have reached the upload limit." : "You may select " + (message > 1 ? "up to " + message + " files." : "one file.")));
			return;
		}
		switch (errorCode) {
		case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
			alert("File too big, File name: " + file.name + ", File size: " + file.size+"KB");
			this.debug("Error Code: File too big, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
			alert("Cannot upload Zero Byte files, File name: " + file.name);
			this.debug("Error Code: Zero byte file, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
			alert("Invalid File Type, File name: " + file.name);
			this.debug("Error Code: Invalid File Type, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		default:
			if (file !== null) {
				alert("Unhandled Error, File name: " + file.name);
			}
			this.debug("Error Code: " + errorCode + ", File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		}
	} catch (ex) {
        this.debug(ex);
    }
}

function swfu_fileDialogComplete(numFilesSelected, numFilesQueued) {
	try {
		/* I want auto start the upload and I can do that here */
		var r=true;
		if(this.customSettings.onSelect){
			var files=new Array();
			for(var i=0;i<numFilesSelected;i++){
				files.push(this.getFile(i));
			}
			r=this.customSettings.onSelect.call(this,this.settings.button_action==SWFUpload.BUTTON_ACTION.SELECT_FILES?files:files[0]);
		}
		if (numFilesQueued > 0 && r) {
			this.startUpload();
		}
	} catch (ex)  {
        this.debug(ex);
	}
}

function uploadStart(file) {
	try {
		/* I don't want to do any file validation or anything,  I'll just update the UI and
		return true to indicate that the upload should start.
		It's important to update the UI here because in Linux no uploadProgress events are called. The best
		we can do is say we are uploading.
		 */
		this.setButtonText("Uploading...");
	}
	catch (ex) {}
	
	return true;
}

function swfu_uploadProgress(file, bytesLoaded, bytesTotal) {
	try {
		var percent = Math.ceil((bytesLoaded / bytesTotal) * 100);
		this.setButtonText("Uploading...("+percent+"%)");
	} catch (ex) {
		this.debug(ex);
	}
}

function swfu_uploadSuccess(file, serverData) {
	try {
		if(serverData){
			var result=eval("("+serverData+")");
			if(this.customSettings.fileInputId){
				document.getElementById(this.customSettings.fileInputId).value=result.url;
				result.fileInputId=this.customSettings.fileInputId;
			}
			if(this.customSettings.previewSize){
				result.previewUrl=result.mediaType+"/"+this.customSettings.previewSize+"/"+result.url;
			}else{
				result.previewUrl=result.mediaType+"/"+result.url;
			}
			if(this.customSettings.previewImg){
				document.getElementById(this.customSettings.previewImg).src=__mediaPath+result.previewUrl;
			}
			if(this.customSettings.onComplete){
				this.customSettings.onComplete.call(this,result);
			}
		}else{
			alert("上传文件\""+file.name+"\"超时!");
		}
	} catch (ex) {
		this.debug(ex);
	}
}

function swfu_uploadError(file, errorCode, message) {
	try {
		switch (errorCode) {
		case SWFUpload.UPLOAD_ERROR.HTTP_ERROR:
			alert("Upload Error, File name: " + file.name );
			this.debug("Error Code: HTTP Error, File name: " + file.name + ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_FAILED:
			alert("Upload Failed, File name: " + file.name );
			this.debug("Error Code: Upload Failed, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.IO_ERROR:
			alert("Server (IO) Error,File name: " + file.name);
			this.debug("Error Code: IO Error, File name: " + file.name + ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.SECURITY_ERROR:
			alert("Security Error, File name: " + file.name );
			this.debug("Error Code: Security Error, File name: " + file.name + ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
			alert("Upload Limit Exceeded, File name: " + file.name + ", File size: " + file.size + "KB");
			this.debug("Error Code: Upload Limit Exceeded, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.FILE_VALIDATION_FAILED:
			alert("File Validation Failed, File name: " + file.name);
			this.debug("Error Code: File Validation Failed, File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED:
			// If there aren't any files left (they were all cancelled) disable the cancel button
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED:
			break;
		default:
			alert("Unhandled Error: " + errorCode + ", File name: " + file.name);
			this.debug("Error Code: " + errorCode + ", File name: " + file.name + ", File size: " + file.size + ", Message: " + message);
			break;
		}
	} catch (ex) {
        this.debug(ex);
    }
}

function swfu_uploadComplete(file) {
	try {
		/*  I want the next upload to continue automatically so I'll call startUpload here */
		if (this.getStats().files_queued > 0) {
			this.startUpload();
		} else {
			this.setButtonText(this.settings.custom_settings.button_text);
		}
	} catch (ex) {
		this.debug(ex);
	}
}
/**
 * fileInputId 上传文件后的路径输入框
 * uploadCategory 输入上传的目录名,如category,product等,对应前台media下的目录,默认是others目录
 * uploadFileTypes *.*;*.jpg; *.jpeg; *.psd; *.png; *.gif; *.zip; *.mp3; *.doc; *.txt。默认为*.*,表示支持所有类型（需支持多个指定类型的用分号分隔）
 * onComplete 当上传完毕后，调用的回调函数（注意是每当上传完毕一个文件的都会调用）
 * onSelect 浏览文件后，调用的回调函数
 * objId 图片所属对象Id，上传图片按此Id作为路径保存，（产品图片，包括sku，更多媒体图片，都以产品Id作路径保持；目录图片，以其父目录Id作路径保持）
 * isMultiFiles 是否同时上传多个文件，默认false
 * btnPlaceHolderId 上传按钮显示位置位置
 * button_text 按钮文本内容。默认为上传文件
 * previewSize 预览图片大小，a,b,c,d.默认为v
 * fileSizeLimit 上传图片最大限制，例如 2MB/10MB。默认为2MB
 * @param {Object} settings 
 */
function initSwfUploadInstance(settings){
	var _Settings={upload_url : __ctxPath+"/dialog/upload.html",
		post_params: {"objId":settings.objId?settings.objId:"",mediaType:settings.uploadCategory?settings.uploadCategory:"other","uploadWidget" : "SWFUpload",fileImageSize:settings.fileImageSize?settings.fileImageSize:"v"},
		use_query_string:true,
		file_size_limit : settings.fileSizeLimit?settings.fileSizeLimit:"2MB",
		file_types : settings.uploadFileTypes?settings.uploadFileTypes:"*.*",
		file_types_description : "file_types_description Images",
		file_upload_limit : "0",
		
		// Event Handler Settings - these functions as defined in Handlers.js
		// The handlers are not part of SWFUpload but are part of my website and control how
		// my website reacts to the SWFUpload events.
		file_queue_error_handler : swfu_fileQueueError,
		file_dialog_complete_handler : swfu_fileDialogComplete,
		upload_progress_handler : swfu_uploadProgress,
		upload_error_handler : swfu_uploadError,
		upload_success_handler : swfu_uploadSuccess,
		upload_complete_handler : swfu_uploadComplete,
		//queue_complete_handler : queueComplete,
		
		// Button Settings
		button_image_url : __ctxPath+"/scripts/swfupload/SmallSpyGlassWithTransperancy_17x18.png",
		button_placeholder_id : settings.btnPlaceHolderId,
		button_width : 115,
		button_action : settings.isMultiFiles?SWFUpload.BUTTON_ACTION.SELECT_FILES:SWFUpload.BUTTON_ACTION.SELECT_FILE,
		button_height : 18,
		button_text : '<span class="button">'+(settings.button_text?settings.button_text:'上传文件')+'</span>',
		button_text_style : '.button {font-family: Helvetica, Arial, sans-serif; font-size: 12pt; } .buttonSmall { font-size: 10pt; }',
		button_text_top_padding : 0,
		button_text_left_padding : 18,
		button_window_mode : SWFUpload.WINDOW_MODE.TRANSPARENT,
		button_cursor : SWFUpload.CURSOR.HAND,
	
		// Flash Settings
		flash_url : __ctxPath+"/scripts/swfupload/swfupload.swf",
		custom_settings : {
			cancelButtonId : "btnCancel",
			onSelect:settings.onSelect?settings.onSelect:"",
			onComplete:settings.onComplete?settings.onComplete:"",
			previewSize:settings.previewSize?settings.previewSize:"",
			previewImg:settings.previewImg?settings.previewImg:"",
			fileInputId:settings.fileInputId?settings.fileInputId:"",
			button_text:'<span class="button">'+(settings.button_text?settings.button_text:'上传文件')+'</span>'
		},
		// Debug Settings
		debug : false
	};
	return new SWFUpload(_Settings);
}