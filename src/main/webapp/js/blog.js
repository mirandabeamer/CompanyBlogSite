/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/* global tinymce */

$(document).ready(function () {
//    tinymce.init({
//        selector: 'textarea', // change this value according to your HTML
//        plugin: 'a_tinymce_plugin',
//        a_plugin_option: true,
//        a_configuration_option: 400
//
//    });
    tinymce.init({
        selector: 'textarea',
        plugins: 'image code',
        toolbar: 'undo redo | link image | code',
        image_title: true,
        automatic_uploads: true,
        file_picker_types: 'image',
        file_picker_callback: function (cb, value, meta){
            var input = document.createElement('input');
            input.setAttribute('type', 'file');
            input.setAttribute('accept', 'image/*')
            
            input.onchange = function () {
                var file = this.files[0];
                var reader = new FileReader();
                reader.onload = function () {
                    var id = 'blobid' + (new Date()).getTime();
                    var blobCache = tinymce.activeEditor.editorUpload.blobCache;
                    var base64 = reader.result.split(',')[1];
                    var blobInfo = blobCache.create(id, file, base64);
                    blobCache.add(blobInfo);
                    
                    cb(blobInfo.blobUri(), {title:file.name});
                };
                reader.readAsDataURL(file);
            };
            input.click();
        },

        /* without images_upload_url set, Upload tab won't show up*/
//        images_upload_url: '/images',
//
//        /* we override default upload handler to simulate successful upload*/
//        images_upload_handler: function (blobInfo, success, failure) {
//            setTimeout(function () {
//                /* no matter what you upload, we will turn it into TinyMCE logo :)*/
//                success('http://moxiecode.cachefly.net/tinymce/v9/images/logo.png');
//            }, 2000);
//        },
        content_style: 'body { font-family:Helvetica,Arial,sans-serif; font-size:14px }'
    });

});




