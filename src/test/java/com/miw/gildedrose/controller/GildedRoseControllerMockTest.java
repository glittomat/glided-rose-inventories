package com.miw.gildedrose.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.miw.gildedrose.domain.BuyItemDetails;
import com.miw.gildedrose.domain.Item;
import com.miw.gildedrose.domain.messaging.BuyGildedRoseInvenotryRequest;
import com.miw.gildedrose.persistence.GildedRoseRepository;
import com.miw.gildedrose.service.GildedRoseService;

@SpringBootTest
@ActiveProfiles("test")
public class GildedRoseControllerMockTest {

	 /** The mapper. */
    private static ObjectMapper mapper = new ObjectMapper();
//    
//    @BeforeEach
//    public void setUp() throws Exception {
//        MockitoAnnotations.initMocks(this);
//    }
//    
//    @Mock
//    private Item item;
    
    private MockMvc mockMvc;
    
    @Mock
    private GildedRoseService service;
    
    
    @InjectMocks
    private GildedRoseController controller;
    
    @Mock
    private GildedRoseRepository gildedRoseRepository;
        
    @BeforeEach
    void initService() {
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }
    
    @AfterEach
    void closeService() throws Exception {
//        closeable.close();
    }
    
    
    @BeforeEach
    public void createTask() {
//      item = new Item(100L, "Book", "The Book", 100, null);
//      given().basePath("/tasks").contentType("application/json").body(item).when().post();
    }
    
    @Test
    public void getAllInventoriesPositiveTest() throws Exception {    	
    	List<Item> itemEniItems = createItemEntities();    	
        when(service.getAllGlideRoseInventories()).thenReturn(itemEniItems);
        
        mockMvc.perform(get("/getAllInventories")).andExpect(status().isOk());
    }
    
    @Test
    public void getInventoryPositiveTest() throws Exception {  	
    	List<Item> itemEniItems = createItemEntities();
    	Item item = itemEniItems.get(0);    	
        when(service.getGlideRoseInventory(anyString())).thenReturn(item);
        
        mockMvc.perform(get("/getInventory/Book")).andExpect(status().isOk());
    }
    
    @Test
    public void getInventoryBadUrlTest() throws Exception {  	
    	List<Item> itemEniItems = createItemEntities();
    	Item item = itemEniItems.get(0);    	
        when(service.getGlideRoseInventory(anyString())).thenReturn(item);
        
        mockMvc.perform(get("/getInventory")).andExpect(status().is4xxClientError());
    }
    
    @Test
    public void getInventoryNoItemTest() throws Exception {  	
    	when(service.getGlideRoseInventory(anyString())).thenReturn(null);
    	
        mockMvc.perform(get("/getInventory/noitem")).andExpect(status().is2xxSuccessful());
    }

    @Test
    public void buyInventoryTest() throws Exception {  	    	
    	BuyItemDetails buyItemDetails = createBuyItemDetails();
    	BuyGildedRoseInvenotryRequest request = new BuyGildedRoseInvenotryRequest(buyItemDetails);
    	
        ObjectMapper mapper = new ObjectMapper();
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(request);
        
        when(service.buyGlideRoseInventory(any())).thenReturn(buyItemDetails);
        mockMvc.perform(post("/buyInventory").contentType(MediaType.APPLICATION_JSON).content(requestJson))
        .andExpect(status().isOk()); 
    }
    
    
    private List<Item> createItemEntities() {
    	Item item1 = new Item();
    	item1.setName("Book");
    	item1.setDescription("The Book");
    	item1.setPrice(100.00);
    	
    	Item item2 = new Item();
    	item2.setName("Chocolate");
    	item2.setDescription("KitKat");
    	item2.setPrice(10.00);

    	List<Item> itemEniItems = new ArrayList<>();
    	itemEniItems.add(item1);
    	itemEniItems.add(item2);

    	return itemEniItems;
    }
    
    private BuyItemDetails createBuyItemDetails(){   	
    	BuyItemDetails buyItemDetails = new BuyItemDetails();
    	buyItemDetails.setItemEntities(createItemEntities());
    	buyItemDetails.setUserInfo("test");
    	return buyItemDetails;
    }
    
    /**
     * Initialize security context details.
     */
//    private void initializeSecurityContextDetails() {
//        SecurityContext context = new SecurityContextImpl();
//        List<SimpleGrantedAuthority> auth = new ArrayList<>();
//        auth.add(new SimpleGrantedAuthority("test"));
//        Authentication authentication = new RememberMeAuthenticationToken("aa", ud, auth);
//        context.setAuthentication(authentication);
//        SecurityContextHolder.setContext(context);
//    }

}
