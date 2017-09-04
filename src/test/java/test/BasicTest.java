package test;


import com.baomidou.framework.spring.SpringContextHolder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by zizou on 2017/5/18.
 */


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/servlet-context.xml")
public class BasicTest {

    @Autowired
    protected ApplicationContext applicationContext;

    @Autowired
    protected SpringContextHolder springContextHolder;


}
