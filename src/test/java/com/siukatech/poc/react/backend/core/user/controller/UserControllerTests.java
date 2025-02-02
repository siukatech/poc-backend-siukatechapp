package com.siukatech.poc.react.backend.core.user.controller;

import com.siukatech.poc.react.backend.core.business.dto.UserDto;
import com.siukatech.poc.react.backend.core.business.dto.UserViewDto;
import com.siukatech.poc.react.backend.core.user.service.UserService;
import com.siukatech.poc.react.backend.core.security.evaluator.PermissionControlEvaluator;
import com.siukatech.poc.react.backend.core.security.provider.AuthorizationDataProvider;
import com.siukatech.poc.react.backend.core.web.annotation.v1.ProtectedApiV1Controller;
import com.siukatech.poc.react.backend.core.web.context.EncryptedBodyContext;
import com.siukatech.poc.react.backend.core.web.helper.EncryptedBodyAdviceHelper;
import com.siukatech.poc.react.backend.core.web.micrometer.CorrelationIdHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.authentication;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@Slf4j
@WebMvcTest(controllers = {UserController.class}
//        , excludeAutoConfiguration = {SecurityAutoConfiguration.class}
        , properties = {
        "client-id=XXX"
        , "client-secret=XXX"
        , "client-realm=react-backend-realm"
        , "oauth2-client-keycloak=http://keycloak-host-name"
        , "oauth2-client-redirect-uri=http://redirect-host-name/redirect"
        , "spring.profiles.active=dev"
        , "logging.level.com.siukatech.poc.react.backend.core: TRACE"
    }
//    , useDefaultFilters = false
)
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTests {

    protected static final Logger log = LoggerFactory.getLogger(UserControllerTests.class);

//    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @MockBean
    private UserService userService;
//    @MockBean
//    private UserRepository userRepository;
    @MockBean
    private JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter;
    @SpyBean
    private EncryptedBodyContext encryptedBodyContext;
    @MockBean
    private EncryptedBodyAdviceHelper encryptedBodyAdviceHelper;
//    @MockBean
//    private InMemoryClientRegistrationRepository clientRegistrationRepository;
    @MockBean
    private AuthorizationDataProvider authorizationDataProvider;
    @MockBean
    private PermissionControlEvaluator permissionControlEvaluator;
//    @MockBean
//    protected Tracer tracer;
    @MockBean
    protected CorrelationIdHandler correlationIdHandler;
    @MockBean
    private OAuth2ClientProperties oAuth2ClientProperties;
    // @MockBean
    // protected ProblemDetailExtMapper problemDetailExtMapper;


//    private UserEntity prepareUserEntity_basic() {
//        UserEntity userEntity = new UserEntity();
//        userEntity.setUserId("app-user-01");
//        userEntity.setName("App-User-01");
//        userEntity.setPublicKey("public-key");
//        userEntity.setPrivateKey("private-key");
//        return userEntity;
//    }

    private UserDto prepareUserDto_basic() {
        UserDto userDto = new UserDto();
        userDto.setUserId("app-user-01");
        userDto.setName("App-User-01");
        userDto.setPublicKey("public-key");
//        userDto.setPrivateKey("private-key");
        return userDto;
    }

    private UserViewDto prepareUserViewDto_basic() {
        UserViewDto userViewDto = new UserViewDto();
        userViewDto.setUserId("app-user-01");
        userViewDto.setName("App-User-01");
        userViewDto.setPublicKey("public-key");
//        userViewDto.setPrivateKey("private-key");
        return userViewDto;
    }

    private UsernamePasswordAuthenticationToken prepareAuthenticationToken_basic() {
        List<GrantedAuthority> convertedAuthorities = new ArrayList<GrantedAuthority>();
        UserDetails userDetails = new User("app-user-01", "", convertedAuthorities);
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        return authenticationToken;
    }

//    @BeforeAll
//    public static void init() {
//    }

    @BeforeEach
    public void setup(TestInfo testInfo) {
        Method method = testInfo.getTestMethod().get();
        switch (method.getName()) {
            case "getPublicKey_basic":
            case "getUserInfo_basic":
            default:
        }
        //
//        UsernamePasswordAuthenticationToken authenticationToken = prepareAuthenticationToken_basic();
//        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //
        if (mockMvc == null) {
            mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext)
                    .apply(springSecurity())
                    .build();
        }
        //
        log.debug("setup - SecurityContextHolder.getContext.getAuthentication: [" + SecurityContextHolder.getContext().getAuthentication() + "]");
    }
//
//    @Test
////    @WithMockUser("app-user-01")
//    public void getPublicKey_basic() throws Exception {
//        // given
////        UserEntity userEntity = this.prepareUserEntity_basic();
////        when(userRepository.findByUserId(anyString())).thenReturn(Optional.of(userEntity));
//        UserDto userDto = this.prepareUserDto_basic();
//        when(userService.findByUserId(anyString())).thenReturn(userDto);
//
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        log.debug("getPublicKey_basic - authentication: [" + authentication + "]");
//
//        // when
//        RequestBuilder requestBuilder = MockMvcRequestBuilders
//                .post(ProtectedApiV1Controller.REQUEST_MAPPING_URI_PREFIX
//                        + "/users/{targetUserId}/public-key", userDto.getUserId())
//                .with(authentication(prepareAuthenticationToken_basic()))
//                .with(csrf())
//                //.with(SecurityMockMvcRequestPostProcessors.user((UserDetails) authentication.getPrincipal()))
//                .accept(MediaType.APPLICATION_JSON);
//
//        // then / verify
//        MvcResult mvcResult = this.mockMvc.perform(requestBuilder)
//                .andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().string("public-key"))
//                .andReturn();
//
//        // result
//        log.debug("getPublicKey_basic - mvcResult.getResponse.getContentAsString: ["
//                + mvcResult.getResponse().getContentAsString()
//                + "], end");
//
//    }

    @Test
    public void getUserInfo_basic() throws Exception {
        // given
//        UserDto userDto = this.prepareUserDto_basic();
//        when(userService.findByUserId(anyString())).thenReturn(userDto);
        UserViewDto userViewDto = this.prepareUserViewDto_basic();
        when(userService.findUserByUserId(anyString())).thenReturn(userViewDto);

        // when
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post(ProtectedApiV1Controller.REQUEST_MAPPING_URI_PREFIX
                        + "/users/{targetUserId}/user-info"
//                        , userDto.getUserId()
                        , userViewDto.getUserId()
                )
                .with(authentication(prepareAuthenticationToken_basic()))
                .with(csrf())
                .accept(MediaType.APPLICATION_JSON);

        // then / verify
        MvcResult mvcResult = this.mockMvc
                .perform(requestBuilder)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json("{userId: \"app-user-01\"}"))
                .andReturn();

        // result
        log.debug("getUserInfo_basic - mvcResult.getResponse.getContentAsString: ["
                + mvcResult.getResponse().getContentAsString()
                + "], end");

    }

}
