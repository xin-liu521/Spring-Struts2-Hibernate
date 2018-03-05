<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/2/12
  Time: 15:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>上传证件</title>
    <style >
        *{
            padding: 0;
            margin: 0;
        }
        .wrapper{
            width: 320px;
            height: 50px;
            margin: 20px auto;
            position: relative;
            border: 1px solid #f0f0f0;
        }
        input{
            width: 100px;
            height: 30px;
        }
        button{
            position: absolute;
            cursor: pointer;
            pointer-events: none;
            width: 100px;
            height: 30px;
            left: 0;
            top: 0;
        }
        a{
            pointer-events: none;
        }
        .img{
            border: 1px solid #ccc;
            padding: 10px;
        }
    </style >
</head>
<body>
    <div class = "wrapper">
        <input type = "file" accept= "image/*"  id= "img" />
        <button >上传照片 </button >
    </div >

</body>
</html>
<script>
    document.getElementById( 'img').addEventListener( 'change', function () {
        var reader = new FileReader();
        reader.onload = function (e) {
            //调用图片压缩方法：
            //compress(this.files[0],fileSize);
            console.log(e);
        };
        reader.readAsDataURL(this.files[0]);
        console.log(this.files[0]);
        var fileSize = Math.round( this.files[0].size/1024/1024) ; //以M为单位
        //this.files[0] 该信息包含：图片的大小，以byte计算 获取size的方法如下：this.files[0].size;

        //compress(this.files[0],fileSize);

    }, false);

    //最终实现思路：
    //1、设置压缩后的最大宽度 or 高度；
    //2、设置压缩比例，根据图片的不同size大小，设置不同的压缩比。

    function compress(res,fileSize) { //res代表上传的图片，fileSize大小图片的大小
        var img = new Image(),
                maxW = 640; //设置最大宽度

        img.onload = function () {
            var cvs = document.createElement( 'canvas'),
                    ctx = cvs.getContext( '2d');

            if(img.width > maxW) {
                img.height *= maxW / img.width;
                img.width = maxW;
            }

            cvs.width = img.width;
            cvs.height = img.height;

            ctx.clearRect(0, 0, cvs.width, cvs.height);
            ctx.drawImage(img, 0, 0, img.width, img.height);

            var compressRate = getCompressRate(1,fileSize);

            var dataUrl = cvs.toDataURL( 'image/jpeg', compressRate);

            document.body.appendChild(cvs);
            console.log(dataUrl+"--------------");
        }

        img.src = res;
        $("#img").append(img);
    }

    function getCompressRate(allowMaxSize,fileSize){ //计算压缩比率，size单位为MB
        var compressRate = 1;

        if(fileSize/allowMaxSize > 4){
            compressRate = 0.5;
        } else if(fileSize/allowMaxSize >3){
            compressRate = 0.6;
        } else if(fileSize/allowMaxSize >2){
            compressRate = 0.7;
        } else if(fileSize > allowMaxSize){
            compressRate = 0.8;
        } else{
            compressRate = 0.9;
        }

        return compressRate;
    }
</script>
