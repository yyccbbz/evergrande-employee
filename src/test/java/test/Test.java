package test;

import java.io.*;

public class Test {

    /*{ field: 'userName', title: '客户姓名', align: 'center', valign: 'middle' },
    * <field name="id" title="主键"/>
    * `t_member_no`  VARCHAR(50) NOT NULL COMMENT '推荐人会员号' ,
    * */

    public static void main(String[] args) throws IOException {

        File file = new File("E:\\Workspace\\intellij2017\\evergrande-employee\\src\\test\\java\\test\\employee.txt");
//        File file = new File("E:\\Users\\IdeaProjects\\SpringWind\\src\\test\\java\\test\\getSalesDetails.txt");
//        File file = new File("D:\\ideaworkspace\\SpringWind\\src\\test\\java\\test\\advisor.txt");

        if (file.isFile() && file.exists()) { //判断文件是否存在

            InputStreamReader read = new InputStreamReader(new FileInputStream(file));
            BufferedReader bufferedReader = new BufferedReader(read);
            String lineTxt = null;

            StringBuffer sb = new StringBuffer();

            while ((lineTxt = bufferedReader.readLine()) != null) {
                String[] strs = lineTxt.split(",");
                String s1 = strs[0];//中文
                String s2 = strs[1];//表字段
                String s3 = strs[2];//类属性

                String field = "{ field: '" + s3 + "', title: '" + s1 + "', sortable:'true',  align: 'center', valign: 'middle' },";
//                System.err.println(field);

                String excelLine = "<field name=\""+s3+"\" title=\""+s1+"\"/>";
                System.err.println(excelLine);
                sb.append(s3);
                sb.append(",");

            }

            System.out.println("sb = " + sb);
        }


    }

}
