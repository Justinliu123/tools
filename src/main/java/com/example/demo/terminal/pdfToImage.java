package com.example.demo.terminal;


import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.rendering.PDFRenderer;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


public class pdfToImage {
    public static void main(String[] args) {
        pdfSwitchToPicture("D:\\data\\WeChat\\WeChat Files\\wxid_895eeicdxsyn22\\FileStorage\\File\\2024-12\\宏格思龙营业执照.pdf",
                "C:\\Users\\33099\\Desktop\\temp\\temp",
                1, 5);
    }

    public static List<String> extractImage(String pdfPath, Integer pdfPage, String picPath) {
        FileInputStream fis = null;
        PDDocument document = null;
        List<String> imageUrls = new ArrayList<>();
        try {
            File file = new File(picPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            // 打开pdf文件流
            fis = new FileInputStream(pdfPath);
            // 加载 pdf 文档,获取PDDocument文档对象
            document = PDDocument.load(fis);
            PDPage page = document.getPage(pdfPage);
            PDResources resources = page.getResources();
            Iterable<COSName> xObjectNames = resources.getXObjectNames();
            int i = 1;
            if (xObjectNames != null) {
                Iterator<COSName> iterator = xObjectNames.iterator();
                while (iterator.hasNext()) {
                    COSName key = iterator.next();
                    if (resources.isImageXObject(key)) {
                        PDImageXObject image = (PDImageXObject) resources.getXObject(key);
                        BufferedImage bImage = image.getImage();
                        String imageUrl = picPath + File.separator + pdfPage + "-" + i + "." + image.getSuffix();
                        ImageIO.write(bImage, image.getSuffix(), new File(imageUrl));
                        imageUrls.add(imageUrl);
                    }
                    i++;
                }
            }
            fis.close();
            document.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("有异常图片");
        }
        return imageUrls;
    }

    /**
     * 将PDF文档拆分成多张图片，并返回所有图片的路径
     *
     * @param pdfPath
     * @param pictureFolderPath
     * @return
     * @throws Exception
     */
    public static List<String> pdfSwitchToPicture(String pdfPath, String pictureFolderPath, Integer startPage, Integer endPage) {
        List<String> picUrlList = new ArrayList<>();
        File file = new File(pictureFolderPath);
        if (!file.exists()) {
            file.mkdirs();
        }
        try {
            List<byte[]> imageList = handlePdf(pdfPath, startPage, endPage);
            AtomicInteger pictureNameNumber = new AtomicInteger(startPage);
            for (byte[] image : imageList) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                byteArrayOutputStream.write(image);
                String pictureUrl = file.getAbsolutePath() + File.separator + pictureNameNumber.getAndIncrement() + ".jpg";
                byteArrayOutputStream.writeTo(new FileOutputStream(pictureUrl));
                picUrlList.add(pictureUrl);
                byteArrayOutputStream.close();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return picUrlList;
    }

    /**
     * 处理PDF文档
     *
     * @param pdfPath
     * @return
     * @throws Exception
     */
    public static List<byte[]> handlePdf(String pdfPath, Integer startPage, Integer endPage) throws Exception {
        File pdfFile = new File(pdfPath);
        //加载PDF文档
        PDDocument pdDocument = PDDocument.load(pdfFile);
        //创建PDF渲染器
        PDFRenderer pdfRenderer = new PDFRenderer(pdDocument);
        int pageNum = endPage != null ? endPage : pdDocument.getNumberOfPages();
        if(endPage == null || endPage > pdDocument.getNumberOfPages() || endPage < 0) {
            pageNum = pdDocument.getNumberOfPages();
        }
        List<byte[]> list = new ArrayList<>();
        for (int i = startPage - 1; i < pageNum; i++) {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            //将PDF的每一页渲染成一张图片
            BufferedImage image = pdfRenderer.renderImageWithDPI(i, 300);
            ImageIO.write(image, "jpg", outputStream);
            list.add(outputStream.toByteArray());
            outputStream.close();
        }
        pdDocument.close();
        return list;
    }
}
