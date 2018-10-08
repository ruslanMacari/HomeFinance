package ruslan.macari.dao.impl;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.orm.hibernate3.LocalSessionFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ruslan.macari.dao.CurrencyDAO;
import ruslan.macari.domain.Currency;

@Service
public class CurrencyDAOImpl implements CurrencyDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyDAOImpl.class);

    private SessionFactory sessionFactory;

    @Autowired
    @Qualifier(value = "sessionFactory")
    public void setSessionFactory(SessionFactory sf) {
        this.sessionFactory = sf;
    }

    @Override
    @Transactional
    public void addCurrency(Currency currency) {
        Session session = this.sessionFactory.getCurrentSession();
        session.persist(currency);
        LOGGER.info("Currency saved successfully, Currency Details=" + currency);
        //SessionFactory b = new LocalSessionFactoryBean();
        
    }

    @Override
    @Transactional
    public void updateCurrency(Currency currency) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    @Transactional
    public List<Currency> listCurrency() {
        Session session = this.sessionFactory.getCurrentSession();
        List<Currency> currencyList = session.createQuery("from Currency").list();
        for (Currency currency : currencyList) {
            LOGGER.info("User List::" + currency);
        }
        return currencyList;
    }

}
