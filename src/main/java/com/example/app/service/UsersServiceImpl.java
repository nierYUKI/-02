package com.example.app.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.app.domain.Users;

import jakarta.servlet.http.HttpSession;
@Service
@Transactional(rollbackFor = Exception.class)
public class UsersServiceImpl implements UsersService {

  private final HttpSession httpSession;

  public UsersServiceImpl(HttpSession httpSession) {
      this.httpSession = httpSession;
  }

  @Override
  public Users getLoggedInUser() {
      return (Users) httpSession.getAttribute("loggedInUser");
  }
}


