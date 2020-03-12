package com.vortex.cloud.dao.impl;

import com.vortex.cloud.dao.IUserDao;
import com.vortex.cloud.domain.UserInfo;
import com.vortex.cloud.vfs.data.hibernate.repository.SimpleHibernateRepository;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl extends SimpleHibernateRepository<UserInfo, String> implements IUserDao {
    @Override
    public DetachedCriteria getDetachedCriteria() {
        DetachedCriteria criteria = DetachedCriteria.forClass(getPersistentClass(), "user");
        criteria.add(Restrictions.eq("beenDeleted", 0));
        return criteria;
    }
}
