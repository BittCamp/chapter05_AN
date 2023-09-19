package spring.conf;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@Configuration
@PropertySource("classpath:spring/db.properties")
@EnableTransactionManagement
public class SpringConfiguration {
	private @Value("${jdbc.driver}") String driver;
	private @Value("${jdbc.url}") String url;
	private @Value("${jdbc.username}") String username;
	private @Value("${jdbc.password}") String password;
	
	@Bean
	public BasicDataSource dataSource(){ // 아이디가 메소드의 이름으로 잡히게 된다.
		
		BasicDataSource basicDataSource = new BasicDataSource();
		basicDataSource.setDriverClassName(driver);
		basicDataSource.setUrl(url);
		basicDataSource.setUsername(username);
		basicDataSource.setPassword(password);
		
		return basicDataSource;
	}
	//sqlSessionFactory도 있어야되.
	@Bean 
	public SqlSessionFactory sqlSessionFactory() throws Exception{ //예외처리는 틀렸다는거 아니다. 그냥 자바에서 강제로 해주라고 하는거다.
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource()); // sqlSessionFactoryBean.setDataSource(메소드 호출);
		sqlSessionFactoryBean.setConfigLocation(new ClassPathResource("spring/mybatis-config.xml"));
		sqlSessionFactoryBean.setMapperLocations(new ClassPathResource("user/dao/userMapper.xml"));
		
		return sqlSessionFactoryBean.getObject(); // SqlSessionFactory타입이다 sqlSessionFactoryBean.getObject()는
	}
	
	/* 이거를 자바방식으로 고친거다.
	 * <bean id="sqlSessionFactory"
	 * class="org.mybatis.spring.SqlSessionFactoryBean"> <property name="dataSource"
	 * ref="dataSource" /> <!--데이터 소스 --> <property name="configLocation"
	 * value="classpath:spring/mybatis-config.xml"/><!--환경설정파일 --> <property
	 * name="mapperLocations" value="classpath:user/dao/userMapper.xml"/><!-- 매퍼파일
	 * 표시 --> </bean>
	 * 프로퍼티는 set으로 설정해주면된다. 안에 set메소드 안써줘도 되게해준거기떄문에.
	 */
	
	@Bean
	public SqlSessionTemplate sqlSession() throws Exception{ //위에 Exception걸린놈 때문에 같이 가져와야되서 강제 예외처리뜸.
		SqlSessionTemplate sqlSessionTemplate = new SqlSessionTemplate(sqlSessionFactory());
		return sqlSessionTemplate;
	}
	//	<constructor-arg ref="sqlSessionFactory"/> 이 생성자 부분을 매꿔야된다.
	
	@Bean
	public DataSourceTransactionManager transactionManager(){
		DataSourceTransactionManager dataSourceTransactionManager
		= new DataSourceTransactionManager(dataSource());
		return dataSourceTransactionManager;
	}
	//<constructor-arg ref="dataSource" />
}
