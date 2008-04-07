package sigeco.utils.junit;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.sql.Connection;
import java.sql.SQLException;

import junit.framework.TestCase;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * Extends the common JUnit test case to provide support for Spring.
 * @author julien
 */
public class SpringTestCase extends TestCase {

	private String beanFactoryFile;

	private ListableBeanFactory beanFactory;

	private SessionFactory sessionFactory;

	private Session session;

	/**
	 * C'tor
	 *
	 * @param beanFactoryFile that should be classpath-searched
	 */
	public SpringTestCase(final String beanFactoryFile) {
		this.beanFactoryFile = beanFactoryFile;
	}

	/**
	 * SetUp.
	 *
	 * Loads the specified beanFactory.
	 *
	 * Finds a SessionFactory and binds a Session to the current thread.
	 *
	 * Autowires declared fields to existing beans of the same name and type.
	 *
	 * @throws Exception might happen, let's hope not.
	 */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		this.initBeanFactory();
		this.locateSessionFactory();
		this.bindSession();
		this.wireBeans();
	}

	/**
	 * Wires all declared fields to beans of the same name and type.
	 */
	private void wireBeans() {
		Field[] fields = this.getClass().getDeclaredFields();
		for (final Field field : fields) {
			String name = field.getName();
			Class< ? > clazz = field.getType();
			final Object obj = this;
			
			final Object bean = this.beanFactory.getBean(name, clazz);
			if (bean != null) {
				AccessController.doPrivileged(new PrivilegedAction<Object>() {
					public Object run() {
						field.setAccessible(true);
						try {
							field.set(obj, bean);
						} catch (IllegalArgumentException e) {
							e.printStackTrace();
						} catch (IllegalAccessException e) {
							e.printStackTrace();
						}
						field.setAccessible(false);
						return null;
					}
				});
			}
		}
	}

    /**
     * Binds a Hibernate Session to the current Thread
     */
    public final void bindSession() {
        if (TransactionSynchronizationManager.hasResource(this.sessionFactory)) {
            return;
        }
        Session s = this.getSession();
        try {
            final Connection conn = s.connection();
            conn.setAutoCommit(false);
            conn.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        bindSession(s);
    }

    /**
     * Binds the given session to the current transaction, opening the
     * transaction.
     * 
     * @param s
     *            the {@link Session}
     */
    protected final void bindSession(final Session s) {
        Transaction tx = s.getTransaction();
        if (tx == null) {
            tx = s.beginTransaction();
        }
        if (!tx.isActive()) {
            tx.begin();
        }
        SessionHolder sh = new SessionHolder(s);
        sh.setTransaction(tx);
        TransactionSynchronizationManager.bindResource(this.sessionFactory, sh);
    }

	/**
	 * Locates a SessionFactory in the factory.
	 */
	private void locateSessionFactory() {
		Object sf = this.beanFactory.getBean("sessionFactory", SessionFactory.class);
		if (sf != null) {
			this.sessionFactory = (SessionFactory) sf;
		} else {
			throw new IllegalStateException("No Session Factory in bean file!");
		}
	}

	/**
	 * Initializes the BeanFactory.
	 */
	private void initBeanFactory() {
		this.beanFactory = new ClassPathXmlApplicationContext(this.beanFactoryFile);
	}


    /**
     * 
     * @param transaction
     *            the Transaction to rollback if its opened
     */
    private void rollbackTransaction(final Transaction transaction) {
        if ((transaction != null)) {
            transaction.rollback();
        }
    }

    /**
     * Unbinds the Session associated to the current Thread if any, rolling back
     * the transaction if it is not commited
     */
    public final void unbindSession() {
        if ((this.session != null) && TransactionSynchronizationManager.hasResource(this.sessionFactory)) {
            if (this.session.isOpen()) {
                this.rollbackTransaction(this.session.getTransaction());
                this.session.disconnect();
                this.session.close();
                this.session = null;
            }
            TransactionSynchronizationManager.unbindResource(this.sessionFactory);
        }
        
        try {
			((DisposableBean) this.beanFactory).destroy();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.beanFactory = null;
    }
	
	/**
	 * TearDown.
	 *
	 * Rolls back and unbinds the Session bound to the current thread.
	 *
	 * Closes the beanFactory.
	 *
	 * @throws Exception might happen, let's hope not.
	 */
	@Override
	protected void tearDown() throws Exception {
		this.unbindSession();
	}

	/**
	 * @return the beanFactory
	 */
	public ListableBeanFactory getBeanFactory() {
		return this.beanFactory;
	}

	/**
	 * @return the sessionFactory
	 */
	public SessionFactory getSessionFactory() {
		return this.sessionFactory;
	}

    /**
     * @return the session
     */
    public final Session getSession() {
        if (this.session == null) {
            this.session = this.sessionFactory.openSession();
        }
        return this.session;
    }

}
