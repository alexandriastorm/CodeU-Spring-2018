package codeu.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import codeu.model.store.basic.UserStore;
import codeu.model.store.basic.ConversationStore;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import codeu.model.data.User;

import java.util.UUID;
import java.time.Instant;

public class ProfileServletTest {

  private ProfilePageServlet profilePageServlet;
  private UserStore mockUserStore;
  private HttpServletRequest mockRequest;
  private HttpServletResponse mockResponse;
  private RequestDispatcher mockRequestDispatcher;
  private ConversationStore mockConversationStore;


  @Before
  public void setup() {
    profilePageServlet = new ProfilePageServlet();
    mockRequest = Mockito.mock(HttpServletRequest.class);
    mockResponse = Mockito.mock(HttpServletResponse.class);
    mockRequestDispatcher = Mockito.mock(RequestDispatcher.class);
    Mockito.when(mockRequest.getRequestDispatcher("/WEB-INF/view/profiles.jsp"))
            .thenReturn(mockRequestDispatcher);
    mockResponse = Mockito.mock(HttpServletResponse.class);
  }

  @Test
  public void testDoGet() throws IOException, ServletException {
    profilePageServlet.doGet(mockRequest, mockResponse);
    Mockito.verify(mockRequestDispatcher).forward(mockRequest, mockResponse);

    User testUser = new User(UUID.randomUUID(), "Test User", "Test Password", Instant.now());

    mockConversationStore = Mockito.mock(ConversationStore.class);
    profilePageServlet.setConversationStore(mockConversationStore);

    mockUserStore = Mockito.mock(UserStore.class);
    profilePageServlet.setUserStore(mockUserStore);
    Mockito.when(mockUserStore.getUser("Test User")).thenReturn(testUser);

    String username =  mockRequest.getParameter("username");
    Mockito.when(username).thenReturn("Test User");

    Mockito.when(mockRequest.getParameter("password")).thenReturn("Test Password");
    Mockito.when(mockRequest.getRequestURI()).thenReturn("/users/test Test User");


  }
}
