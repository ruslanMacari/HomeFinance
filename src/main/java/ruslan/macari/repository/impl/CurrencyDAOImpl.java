package ruslan.macari.repository.impl;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ruslan.macari.repository.CurrencyDAO;
import ruslan.macari.domain.Currency;

@Repository
@Transactional
public class CurrencyDAOImpl implements CurrencyDAO {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sf) {
        sessionFactory = sf;
    }

    @Override
    public void addCurrency(Currency currency) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(currency);
        
    }

    @Override
    public void updateCurrency(Currency currency) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Currency> listCurrency() {
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("from Currency").list();
    }

}
