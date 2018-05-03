package com.fww.controller;

import com.baomidou.mybatisplus.plugins.Page;
import com.fww.*;
import com.fww.platform.BaseController;
import com.fww.platform.FunctionException;
import com.fww.platform.OmsContext;
import com.fww.platform.OmsFreeMarkerConfigurer;
import com.fww.redis.RedisSlave;
import com.fww.result.Result;
import com.fww.result.ReturnDTO;
import com.fww.rocketmq.RocketMqProducer;
import com.fww.service.DemoService;
import com.fww.utils.ExcelUtil;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.swagger.annotations.*;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

@Api(description = "用户信息", value = "", protocols = "HTTP")
@RestController
//@SuppressWarnings("all")
@RequestMapping("/a")
public class DemoController extends BaseController {
    private static final String TOPIC_OMS_DEMO = "topic-oms-loan";
    private static final String TAG_OMS_DEMO = "topic-oms-tag";
    @Autowired
    private DemoService demoService;
    @Autowired
    private OmsContext omsContext;
    @Autowired
    private OmsFreeMarkerConfigurer omsFreeMarkerConfigurer;

    @RequestMapping(value = "/status")
    public String index() {
        return "hello world!!!!";
    }

    /**
     * 新增
     *
     * @return
     */
    @ApiOperation(value = "新增用户(loan:demo:insert)", notes = "新增用户,传JSON参数", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    //@RequiresPermissions("loan:demo:insert")
    public ReturnDTO insert(@ApiParam(name = "user VO实体", value = "json格式", required = true) @RequestBody DemoVO vo)
            throws FunctionException {
        Result result = this.getResult();

        demoService.insert(result, vo);

        return result.DTO();
    }

    /**
     * 修改
     *
     * @return
     */
    @ApiOperation(value = "修改用户信息(loan:demo:insert)", notes = "根据Id修改用户信息,传JSON参数", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/updateById", method = RequestMethod.POST)
    //@RequiresPermissions("loan:demo:updateById")
    public ReturnDTO updateById(@ApiParam(name = "user VO实体", value = "json格式", required = true) @RequestBody DemoVO vo)
            throws FunctionException {
        Result result = this.getResult();
        try {
            demoService.update(result, vo);
            int count = result.getData();
            if (count == 0) {
//				result.setCode(LoanBizResCodeEnum.BIZ1.getCode());
//				result.setMsg(LoanBizResCodeEnum.BIZ1.getMsg());
            }
        } catch (Exception e) {
            result.setCode(LoanResponseCode.LoanExpResCodeEnum.EXP2.getCode());
            result.setMsg(LoanResponseCode.LoanExpResCodeEnum.EXP2.getMsg());
            result = this.error(result, e);
        } finally {
            this.send(result);
        }
        return result.DTO();
    }

    /**
     * 根据Id删除
     *
     * @return
     */
    @ApiOperation(value = "删除用户信息(loan:demo:insert)", notes = "根据Id删除用户信息", httpMethod = "POST", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/deleteById", method = RequestMethod.POST)
    //@RequiresPermissions("loan:demo:deleteById")
    public ReturnDTO deleteById(@ApiParam(name = "user VO实体", value = "json格式", required = true) @RequestBody DemoVO vo)
            throws FunctionException {
        Result result = this.getResult();
        try {
            demoService.delete(result, vo.getId());
        } catch (Exception e) {
            result = this.error(result, e);
        } finally {
            this.send(result);
        }
        return result.DTO();
    }

    /**
     * 根据Id查询
     *
     * @return
     */
    @ApiOperation(value = "根据Id查询用户信息(loan:demo:insert)", notes = "根据Id查询用户信息", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
//	@ApiResponse(code=200, message="成功", response=DemoVO.class)
    @ApiResponses(value = {@ApiResponse(code = 200, message = "成功", response = DemoVO.class), @ApiResponse(code = 500, message = "服务器内部异常", response = ReturnDTO.class)})
    @RequestMapping(value = "/queryById", method = RequestMethod.GET)
    //@RequiresPermissions("loan:demo:queryById")
    public ReturnDTO queryById(@ApiParam(name = "id", value = "主键", required = true) @RequestParam("id") Integer id)
            throws FunctionException {
//		ProductService productService = omsContext.getBean("productService",ProductService.class);

        Result result = this.getResult();
        try {
            result = demoService.selectById(result, id);
        } catch (Exception e) {
            result = this.error(result, e);
        } finally {
            this.send(result);
        }
        return result.DTO();
    }

    /**
     * 统计
     *
     * @return
     */
    @ApiOperation(value = "统计用户数量(loan:demo:insert)", notes = "根据条件统计用户数量", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/count", method = RequestMethod.GET)
    //@RequiresPermissions("loan:demo:count")
    public ReturnDTO count(@ApiParam(name = "user VO实体", value = "json格式", required = true) @RequestBody DemoVO vo)
            throws FunctionException {
        Result result = this.getResult();
        try {
            demoService.count(result, vo);
        } catch (Exception e) {
            result = this.error(result, e);
        } finally {
            this.send(result);
        }
        return result.DTO();
    }

    /**
     * 列表
     *
     * @return
     */
    @ApiOperation(value = "用户列表(loan:demo:insert)", notes = "用户列表,无参数，查询所有用户", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/selectList", method = RequestMethod.GET)
    //@RequiresPermissions("loan:demo:selectList")
    public ReturnDTO selectList() throws FunctionException {
        //UserSession session = super.getUserInfo();

        Result result = this.getResult();
        try {
            result = demoService.selectList(result);
        } catch (Exception e) {
            result = this.error(result, e);
        } finally {
            this.send(result);
        }

        return result.DTO();
    }

    /**
     * 分页查询
     *
     * @return
     */
    @ApiOperation(value = "分页查询用户列表(loan:demo:insert)", notes = "查询用户列表，支持分页", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/selectPage", method = RequestMethod.GET)
    //@RequiresPermissions("loan:demo:selectPage")
    public ReturnDTO selectPage(@RequestBody Page page) throws FunctionException {
        Result result = this.getResult();
        try {
            result = demoService.selectPage(result, page.getCurrent(), page.getSize());
        } catch (Exception e) {
            result = this.error(result, e);
        } finally {
            this.send(result);
        }

        return result.DTO();
    }

    /**
     * 关联查询
     *
     * @return
     */
    @ApiOperation(value = "关联查询(loan:demo:insert)", notes = "根据给定条件关联查询", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/selectRelation", method = RequestMethod.GET)
    //@RequiresPermissions("loan:demo:selectRelation")
    public ReturnDTO selectRelation(
            @ApiParam(name = "user VO实体", value = "json格式", required = true) @RequestBody DemoVO vo)
            throws FunctionException {
        Result result = this.getResult();
        try {
            result = demoService.selectRelation(result, vo);
        } catch (Exception e) {
            result = this.error(result, e);
        } finally {
            this.send(result);
        }

        return result.DTO();
    }

    /**
     * redis读写数据示例
     */
    @ApiOperation(value = "redis读写数据示例(loan:demo:insert)", notes = "redis读写数据示例", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_VALUE)
    @RequestMapping(value = "/redisReadWrite", method = RequestMethod.GET)
    //@RequiresPermissions("loan:demo:redisReadWrite")
    public void redisReadWrite() {
        String key = "redisDemo";
        long liveTime = 5;//存储时间，单位秒
        RedisSlave.getInstance().set(key, "hello word!", liveTime);
        String value = RedisSlave.getInstance().getString(key);
        System.out.println("---------------->值是：" + value);

        try {
            //暂停6秒，然后读取redis数据
            Thread.sleep((liveTime + 1) * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        value = RedisSlave.getInstance().getString(key);
        System.out.println("---------------->6秒后值是：" + value);
    }

    /**
     * 合同预览
     */
    @RequestMapping(value = "/preview", method = RequestMethod.GET)
    public void preview(HttpServletResponse response) {
        try {
            Map param = this.getParams();
            Template template = this.getTemplate();
            response.reset();
            response.setHeader("content-type", "text/html;charset=utf-8");
            PrintWriter writer = response.getWriter();
            template.process(param, writer);
            writer.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * rocketMq 测试
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/rocketmq/single/anno", method = RequestMethod.GET)
    public void rocketmqSingle(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean bool = RocketMqProducer.getInstance().send(TOPIC_OMS_DEMO, TAG_OMS_DEMO, "Hello word" + System.currentTimeMillis());
        System.out.println("------------->发送结果：" + bool);
    }

    /**
     * rocketMq 测试
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/rocketmq/singleAndKey/anno", method = RequestMethod.GET)
    public void rocketmqSingleAndKey(HttpServletRequest request, HttpServletResponse response) throws IOException {
        boolean bool = RocketMqProducer.getInstance().send(TOPIC_OMS_DEMO, TAG_OMS_DEMO, "{'name':'张三','sex':'F'}", "Hello word" + System.currentTimeMillis());
        System.out.println("------------->发送结果：" + bool);
    }

    /**
     * rocketMq 测试
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/rocketmq/many/anno", method = RequestMethod.GET)
    public void rocketmqMany(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Map<String, String>> lst = new ArrayList<Map<String, String>>();

        Map<String, String> map1 = new HashMap<String, String>();
        map1.put("topic", TOPIC_OMS_DEMO);
        map1.put("tag", TAG_OMS_DEMO);
        map1.put("keys", "{'name':'李四','sex':'F'}");
        map1.put("body", "你好！");

        Map<String, String> map2 = new HashMap<String, String>();
        map2.put("topic", TOPIC_OMS_DEMO);
        map2.put("tag", TAG_OMS_DEMO);
        map2.put("keys", "{'name':'小小','sex':'M'}");
        map2.put("body", "在吗！");

        lst.add(map1);
        lst.add(map2);

        boolean bool = RocketMqProducer.getInstance().send(lst);
        System.out.println("------------->发送结果：" + bool);
    }

    /**
     * 导出Excel
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/exportExcel/anno", method = RequestMethod.GET)
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<Map<String, String>> dataList = this.getDateSource();
        String head = "我的Excel";
        String fields[] = new String[]{"name", "sex"};
        String titles[] = new String[]{"姓名", "性别"};

        File file = ExcelUtil.export(null, null, fields, titles, dataList, null);

        ServletOutputStream out = response.getOutputStream();
        response.setContentLength((int) file.length());
        response.setHeader("Content-Disposition", "attachment;filename=myexcel.xlsx");// 设置在下载框默认显示的文件名
        response.setContentType("application/octet-stream");// 指明response的返回对象是文件流
        // 读出文件到response
        // 这里是先需要把要把文件内容先读到缓冲区
        // 再把缓冲区的内容写到response的输出流供用户下载
        FileInputStream fileInputStream = new FileInputStream(file);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(
                fileInputStream);
        byte[] b = new byte[bufferedInputStream.available()];
        bufferedInputStream.read(b);
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(b);
        // 人走带门
        bufferedInputStream.close();
        outputStream.flush();
        outputStream.close();
    }

    private List<Map<String, String>> getDateSource() {
        List<Map<String, String>> lst = new ArrayList<Map<String, String>>();
        Map<String, String> map1 = new HashMap<String, String>();
        map1.put("name", "张三");
        map1.put("sex", "男");
        Map<String, String> map2 = new HashMap<String, String>();
        map2.put("name", "小红");
        map2.put("sex", "女");

        lst.add(map1);
        lst.add(map2);

        return lst;
    }

    public static void main(String[] args) {
        DemoController dc = new DemoController();
        Map param = dc.getParams();
        Template template = dc.getTemplate();
        System.out.println(template.getEncoding());
        StringWriter writer = new StringWriter();
        try {
            template.process(param, writer);
        } catch (TemplateException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(writer.toString());


    }

    /**
     * 获取模板对象
     *
     * @return
     */
    private Template getTemplate() {
//		Configuration cfg = new Configuration();
        Configuration cfg = omsFreeMarkerConfigurer.getConfiguration();
        StringTemplateLoader stringLoader = new StringTemplateLoader();
        String templateContent = this.getTemplateContent();
        stringLoader.putTemplate("myTemplate", templateContent);

        cfg.setTemplateLoader(stringLoader);

        try {
            Template template = cfg.getTemplate("myTemplate", "utf-8");
            return template;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 获取合同内容
     *
     * @return
     */
    private String getTemplateContent() {
        return "长度是<#if Contacts?size gt 2>${Contacts[5].Address}</#if> ||||"
                + "${Group(Contacts,'Address', 1)} -- ${Group(Contacts,'Address', 0)} -- "
                + "${(Group(Contacts,'BirthDay', 1))?date} || ${(41354.366)?string('0.00')}  "
                + "${Fixed(1564.4056, 2)} || "
                + "${(AddYear(Contacts[1].BirthDay, 20))?date}--${Capital(115687897.36)}!! "
                + "欢迎：${name!}，性别:${sex!}，联系人是${Families[0]!}，Demo是${DemoVOs[0].userName!}， "
                + "map测试：联系人地址是${Contacts[0].Address!}， "
                + "联系人生日是：${(Contacts[1].BirthDay)?string('yyyy-MM-dd')}"
                + " 加减乘除：${interest+finance}, ${interest-finance}, ${interest*finance},${(interest/finance)?string('0.00')}, "
                + "${((Contacts[1].BirthDay)?string('yyyy'))}, ${((Contacts[1].BirthDay)?string('yyyy'))?number}"
                + ", ${((Contacts[1].BirthDay)?string('yyyy'))?number + 1}"
                + ", ${Contacts[1].BirthDay?string('M')}";
    }

    /**
     * 获取模板参数
     *
     * @return
     */
    private Map getParams() {
        Map param = new HashMap();
        // param.put("addYear", new AddYearMethod());
        param.put("name", "建宁");
        param.put("sex", "男");
        // List 存放vo
        List<DemoVO> demoVOs = new ArrayList<>();
        DemoVO one = new DemoVO();
        one.setUserName("赵聪");
        one.setId(1);
        DemoVO two = new DemoVO();
        two.setUserName("老王");
        two.setId(2);
        demoVOs.add(one);
        demoVOs.add(two);

        // List 存放 HashMap
        List<Map<String, Object>> contactsMap = new ArrayList<>();
        Map<String, Object> mapOne = new HashMap<>();
        mapOne.put("Address", "广东河源");
        mapOne.put("BirthDay", new Date());
        Map<String, Object> mapTwo = new HashMap<>();
        mapTwo.put("Address", "广西省");
        mapTwo.put("BirthDay", DateUtils.addMonths(new Date(), 5));
        contactsMap.add(mapOne);
        contactsMap.add(mapTwo);

        // 数组
        String[] families = {"父亲", "母亲", "姐姐", "哥哥"};

        param.put("DemoVOs", demoVOs);
        param.put("Contacts", contactsMap);
        param.put("Families", families);
        param.put("interest", 3.5);
        param.put("finance", 1.6);

        return param;
    }

    /**
     * @return
     */
    public List showConstanceDectiry() {
        List lst = new ArrayList();
        lst.add(new Dictionary("name", "姓名"));
        lst.add(new Dictionary("sex", "性别"));

        return lst;
    }
}



