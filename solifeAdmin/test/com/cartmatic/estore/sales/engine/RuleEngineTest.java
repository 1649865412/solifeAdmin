
package com.cartmatic.estore.sales.engine;

import java.util.Collection;
import java.util.List;
import java.util.Vector;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cartmatic.estore.common.model.cart.Shoppingcart;
import com.cartmatic.estore.common.model.catalog.ProductSku;
import com.cartmatic.estore.common.model.customer.Customer;
import com.cartmatic.estore.common.model.customer.Membership;
import com.cartmatic.estore.common.model.sales.PromoRule;
import com.cartmatic.estore.framework.test.BaseTransactionalTestCase;
import com.cartmatic.estore.sales.util.ProductSkuMocker;
import com.cartmatic.estore.sales.util.RuleMocker;
import com.cartmatic.estore.sales.util.ShoppingcartMocker;

public class RuleEngineTest extends BaseTransactionalTestCase {

	private static final Log	logger	= LogFactory.getLog(RuleEngineTest.class);
	@Autowired
	private PRuleManager pruleManager;
	
	@Test
	public void testRunCartPromo() {

		try {

			Shoppingcart data = ShoppingcartMocker.getData();
			ShoppingcartMocker.print(data);

			Customer user = new Customer();
			Membership membership = new Membership();
//			membership.setMembershipId(4);
//			user.setMembership(membership);
			user.setAppuserId(3);
			user.setMembershipId(4);
			long startTimeInMilli = System.currentTimeMillis();
			long startTimeInNano = System.nanoTime();

			PRuleEngine engine = new PRuleEngine(PRuleManager
					.getCartPromotionRules());
			// PRuleEngine engine = new PRuleEngine(getData());
			data = engine.run(data, user);

			long endTimeInMilli = System.currentTimeMillis();
			long endTimeInNano = System.nanoTime();

			logger.info(new StringBuffer().append("ur~~~ it cost time ")
					.append(endTimeInMilli - startTimeInMilli).append(
							" milliseconds").toString());
			logger.info(new StringBuffer().append("ur~~~ it cost time ")
					.append(endTimeInNano - startTimeInNano).append(
							" nanoseconds").toString());
			ShoppingcartMocker.print(data);
			logger.info("---");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@Test
	public void testRunCatalogPromo() {

		try {

			Collection<ProductSku> data = ProductSkuMocker.getData();
			ProductSkuMocker.print(data);
		
			long startTimeInMilli = System.currentTimeMillis();
			long startTimeInNano = System.nanoTime();

			PRuleEngine engine = new PRuleEngine(PRuleManager
					.getCatalogPromotionRules());
			// PRuleEngine engine = new PRuleEngine(getData());
			data = engine.run(data);

			long endTimeInMilli = System.currentTimeMillis();
			long endTimeInNano = System.nanoTime();

			logger.info(new StringBuffer().append("ur~~~ it cost time ")
					.append(endTimeInMilli - startTimeInMilli).append(
							" milliseconds").toString());
			logger.info(new StringBuffer().append("ur~~~ it cost time ")
					.append(endTimeInNano - startTimeInNano).append(
							" nanoseconds").toString());
			ProductSkuMocker.print(data);
			logger.info("---");
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public Vector<PRule> getData() {
		List<PromoRule> rules = RuleMocker.getData();
		Vector<PRule> prules = new Vector<PRule>();
		for (PromoRule rule : rules) {
			prules.add(new PRule(rule));
		}
		return prules;
	}

}
