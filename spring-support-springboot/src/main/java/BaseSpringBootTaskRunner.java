import com.xzchaoo.spring.support.core.BaseSpringTaskRunner;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;

/**
 * Created by Administrator on 2017/4/4.
 */
public abstract class BaseSpringBootTaskRunner extends BaseSpringTaskRunner implements ApplicationRunner {
	public BaseSpringBootTaskRunner(String name) {
		super(name);
	}

	//@Override
	public void run(ApplicationArguments args) throws Exception {
		schedule(getFirstDelay());
	}
}
