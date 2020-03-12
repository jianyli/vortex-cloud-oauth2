package com.vortex.cloud.service.impl;

import com.vortex.cloud.dao.IUserDao;
import com.vortex.cloud.domain.UserInfo;
import com.vortex.cloud.service.IUserService;
import com.vortex.cloud.vfs.common.exception.ServiceException;
import com.vortex.cloud.vfs.data.support.SearchFilter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements IUserService {

    @Autowired
    private IUserDao userDao;

    @Override
    public UserInfo findUserByUsername(String username) {
        if (StringUtils.isBlank(username)) {
            throw new ServiceException("参数不能为空");
        }
        List<SearchFilter> filters = new ArrayList<>();
        filters.add(new SearchFilter("username", SearchFilter.Operator.EQ, username));
        List<UserInfo> userInfos = userDao.findListByFilter(filters, null);
        if (CollectionUtils.isEmpty(userInfos)) {
            return null;
        }
        return userInfos.get(0);

    }

    @Override
    public void save(String username, String password) {
        if (StringUtils.isAnyBlank(username, password)) {
            throw new ServiceException("参数不能为空");
        }
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(username);
        userInfo.setPassword(password);
        userDao.save(userInfo);
    }
}
