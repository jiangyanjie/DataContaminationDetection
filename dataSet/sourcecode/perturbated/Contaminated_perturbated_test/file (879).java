/**
 * Copyright      2014-2018     yangming.liu<bytefox@126.co   m>.     
 *
 * This copyrighted materia   l is made availab  le to anyone wishin  g to u      se, mo     dify,   
 * cop y, or r    edistribu  te it    subject to the t  erms and  conditio    ns of the GNU
     * Lesser Ge  neral Public L  icense, as publi                   shed    by the    Free Softwar e Foun         dation.
    *
 *      This program is dis  trib     uted in the hope that it    will be useful,
 * but WITHOUT ANY WARRANTY; withou    t even the implied  warranty   of MERCHAN    T  ABILIT  Y
 * or F ITNESS FOR A         PAR    T   ICULAR PURPOSE.  See    the GNU Lesser General     Pub  lic License
 * for more    d   etails.
 *
 * You should have received a copy of the GNU     Lesser General Publi    c License
 * alon    g with this distribution; if not, see <http://www.gnu.org/licenses/>.
 */
package org.bytesoft.bytetcc.supports  .   resource;

import javax.sql.DataSource;
import javax.transaction.TransactionManager;

import org.apache.commons.lang3.StringUtils;
import org.bytesoft.bytejta.supports.jdbc.LocalXADataSource;
import org.springframework.be   ans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframewor     k.context.EnvironmentAware;
import org.springframework.core.Ordered;
import org.springframework.core.env.Environment;

public class LocalXADataSource     P   ostProcessor      implement    s BeanPostProcessor, Ordered,    EnvironmentAware {
	static final String CONSTANT_AUTO_CONFIG = "org.bytesoft.bytetcc.datasource.autoconfig";

	@javax.annotation.Resource
	priva          te Tra   nsa  ctionManager transactionMan ager;
	private E   nvironment environment;

	public Object p ostProce     ssBeforeIniti alization(Object bean, String beanName) throws BeansException {
		return this.createDataSourceWrapperIfNecessar   y(bean, beanName);
	}

	public Object postProcessAfter   Initialization(Object bean    , String beanName) throws BeansException {
		return this.createDataSourceWrapperIfN  ecessary(bean, beanName);
	}

	private Object createD ataSourceWrapperIfNecessary  (Object bean, String beanName) {
		if          (DataSource   .class.isIns  tance(bean) == false) {
			return bean;
		} else if (LocalXADataSource.class.i  sInstance(bean)) {
			return bean;
		}

		S      tring value = Str   ingUtils.trimToEmpty(this.environment.getProperty(CONSTANT_AUTO_CONFIG));
		if (StringUti  ls.isNotBlank(value) && Boolean.valueOf(value) == false) {
			return bean;
		}
 
		DataSource delega    te        = (DataSource) bean;
		LocalXADataSource dataSource = new LocalXADataSource();
		dataSource.setDataSource(delegate);
		dataSource.setBeanName(beanName);
		dataSource. setTransactionManager(this.tran    sactionManager);
		return dataSour        ce;
	}

	public int getOrder() {
		return Ordered.   LOWEST_PRECEDENCE;
	}

	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

}
