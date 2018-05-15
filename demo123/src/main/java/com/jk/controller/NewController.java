package com.jk.controller;

import com.jk.model.News;
import com.jk.service.NewsService;
import com.jk.service.TestThread;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.poi.xwpf.usermodel.*;
import org.junit.Test;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTTblWidth;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STShd;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STTblWidth;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by asus on 2018/4/25.
 */
@Controller
@RequestMapping(value = "news")
public class NewController {

    @Autowired
    private NewsService newsService;

    @Autowired
    private AmqpTemplate amqpTemplate;

    public static ExecutorService cachedThreadPool = Executors.newCachedThreadPool();


    @RequestMapping(value="say")
    public void say(News news){
        amqpTemplate.convertAndSend("1369", news);
    }

   @GetMapping("say2")
    public void say2(News news){
       for (int i = 0;i < 5;i++){
           cachedThreadPool.execute(new TestThread(newsService,news));
       }
    }

    @RequestMapping(value="/queryNews")
    @ResponseBody
    public List<News> queryNews(){
        return newsService.queryNews();
    }

    @RequestMapping(value = "deleteNews")
    @ResponseBody
    public void deleteNews(Integer newsid){
        newsService.deleteNews(newsid);
    }

    @RequestMapping(value = "addNews")
    @ResponseBody
    public void addNews(News news){
        newsService.addNews(news);
    }

    @RequestMapping(value = "queryByIdNews")
    @ResponseBody
    public News queryByIdNews(Integer newsid){
        return newsService.queryByIdNews(newsid);
    }
    //freeMaker
    @RequestMapping(value = "queryTwoNews")
    public String queryTwoNews(Integer newsid,HttpServletRequest request) throws IOException, TemplateException {
        News news =  newsService.queryByIdNews(newsid);
        System.out.println(news);
        Configuration configuration = new Configuration();
        configuration.setDirectoryForTemplateLoading(new File("E:\\idea\\demo123\\src\\main\\webapp"));
        configuration.setDefaultEncoding("utf-8");
        Template template = configuration.getTemplate("fra.ftl");
        Writer out=new FileWriter(new File("E:\\idea\\demo123\\src\\main\\webapp\\"+newsid+".html"));
        Map root=new HashMap();
        root.put("news", news);
        template.process(root, out);
        out.flush();
        out.close();
        return "/"+newsid+".html";
    }

    @RequestMapping(value = "updateNews")
    @ResponseBody
    public void updateNews(News news){
        newsService.updateNews(news);
    }

    //=====================写到word

    @RequestMapping(value = "importNews")
    public void importNews() throws IOException {

        //空白文档
        XWPFDocument document=new XWPFDocument();

        //添加标题
        XWPFParagraph titleParagraph=document.createParagraph();

        //设置标题 居中
        titleParagraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun titleParagraphRun = titleParagraph.createRun();
        titleParagraphRun.setText("这是大标题:Java Poi");
        titleParagraphRun.setColor("000000");
        titleParagraphRun.setFontSize(20);


        //换行
        XWPFParagraph paragraphm=document.createParagraph();
        XWPFRun  paragraphRunm = paragraphm.createRun();
        titleParagraphRun.setText("\r");

        //段落
        XWPFParagraph firstParagraph=document.createParagraph();
        firstParagraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun  run = firstParagraph.createRun();
        titleParagraphRun.setText("此处是段落部分，Java Poi生成 word文件");
        titleParagraphRun.setColor("696969");
        titleParagraphRun.setFontSize(5);

        //段落背景色
        CTShd ctshd=run.getCTR().addNewRPr().addNewShd();
        ctshd.setVal(STShd.CLEAR);
        ctshd.setFill("97FFFF");

        //换行
        XWPFParagraph paragraph1=document.createParagraph();
        XWPFRun  paragraphRun1 = paragraph1.createRun();
        titleParagraphRun.setText("\r");

        //基本信息表格
        XWPFTable infoTable=document.createTable();
        //去掉表格边框
        //infoTable.getCTTbl().getTblPr().unsetTblBorders();

        //列宽自动分割
        CTTblWidth infoTableWidth=infoTable.getCTTbl().addNewTblPr().addNewTblW();
        infoTableWidth.setType(STTblWidth.DXA);
        infoTableWidth.setW(BigInteger.valueOf(9072));

        List<News> list = newsService.queryNews();

        //第一行表头
        XWPFTableRow infoTableOne = infoTable.getRow(0);
        infoTableOne.getCell(0).setText("新闻编号");
        infoTableOne.addNewTableCell().setText("新闻名称");
        infoTableOne.addNewTableCell().setText("新闻详细");

        for (int i = 0; i < list.size(); i++) {
            XWPFTableRow infoTableTwo = infoTable.createRow();
            String nresd = String.valueOf(list.get(i).getNewsid());
            infoTableTwo.getCell(0).setText(nresd);
            infoTableTwo.getCell(1).setText(list.get(i).getNewsname());
            infoTableTwo.getCell(2).setText(list.get(i).getNewsintr());
        }

        FileOutputStream out=new FileOutputStream(new File("D:\\news.docx"));

        //写入、关闭
        document.write(out);
        out.close();
        System.out.println("poi导入word完成");
    }


}
