package service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.yhb.OrderServiceStart;
import com.yhb.dao.OrderDao;

@RunWith(SpringRunner.class)
@SpringBootTest(classes=OrderServiceStart.class)

public class OrderServiceTest {
	@Autowired
	private OrderDao orderDao; 
	
	@Test
	public void test(){
		System.out.println(orderDao.selectAll());
	}
}
