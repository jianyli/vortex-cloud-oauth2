package com.vortex.cloud.dao;

import com.vortex.cloud.domain.UserInfo;
import com.vortex.cloud.vfs.data.hibernate.repository.HibernateRepository;

public interface IUserDao extends HibernateRepository<UserInfo, String> {
}

