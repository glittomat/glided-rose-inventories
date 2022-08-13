package com.miw.gildedrose.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.miw.gildedrose.domain.BuyItemDetails;
import com.miw.gildedrose.domain.Item;
import com.miw.gildedrose.domain.messaging.BuyGildedRoseInvenotryRequest;
import com.miw.gildedrose.persistence.BuyGlidedRoseRepository;
import com.miw.gildedrose.persistence.GildedRoseRepository;
import com.miw.gildedrose.util.SurgePriceUtil;

@SpringBootTest
@ActiveProfiles("test")
public class GildedRoseServiceMockTest {
	
	@InjectMocks
    private GildedRoseService service;


	/** The GildedRoseRepository */
    @Mock
    private GildedRoseRepository gildedRoseRepository;
    
    /** The BuyGlidedRoseRepository */
    @Mock
    private BuyGlidedRoseRepository buyGlidedRoseRepository;
     
    @Mock
    private SurgePriceUtil surgePriceUtil;
    
    @BeforeEach
    private void initializeSecurityContextDetails() {
        SecurityContext context = new SecurityContextImpl();                       
        Authentication authentication = new UsernamePasswordAuthenticationToken("miw", null, new ArrayList<>());
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);
    }
    

    @Test
    public void getAllGlideRoseInventoriesServiceTest() {       
    	List<Item> itemEniItems = createItemEntities();    	
        when(gildedRoseRepository.findAll()).thenReturn(itemEniItems);
        
        List<Item> items = service.getAllGlideRoseInventories();    
        assertNotNull(items);
        assertEquals(itemEniItems,items );       
    }
    
    @Test
    public void getAllGlideRoseInventoriesServiceEmptyTest() {          
        when(gildedRoseRepository.findAll()).thenReturn(null);        
        
        List<Item> items = service.getAllGlideRoseInventories();        
        assertNull(items);
    }
    
    @Test
    public void getGlideRoseInventoryServiceTest() {       
    	List<Item> itemEniItems = createItemEntities();
    	Item item = itemEniItems.get(0);    	
        when(gildedRoseRepository.findByName(anyString())).thenReturn(item);
        when(surgePriceUtil.calculateSurgeAction(any())).thenReturn(anyDouble());        
        
        Item itemResponse = service.getGlideRoseInventory("Book");       
        assertNotNull(itemResponse);
        assertEquals(item,itemResponse );       
    }

    @Test
    public void getGlideRoseInventoryServiceEmptyTest() {          	
        when(gildedRoseRepository.findByName(anyString())).thenReturn(null);
        when(surgePriceUtil.calculateSurgeAction(any())).thenReturn(anyDouble());
        
        Item itemResponse = service.getGlideRoseInventory("Bo");        
        assertNull(itemResponse);             
    }
    
    @Test
    public void buyInventoryServiceTest() throws Exception {  	    	
    	BuyItemDetails buyItemDetails = createBuyItemDetails();
    	BuyGildedRoseInvenotryRequest request = new BuyGildedRoseInvenotryRequest(buyItemDetails);
    	when(buyGlidedRoseRepository.save(any())).thenReturn(buyItemDetails);
    	when(gildedRoseRepository.findAll()).thenReturn(createItemEntities());
    	BuyItemDetails buyItemDetailsResponse = service.buyGlideRoseInventory(request);       
        assertNotNull(buyItemDetailsResponse);
        assertEquals(buyItemDetails,buyItemDetailsResponse );           
    }
    
    @Test
    public void buyInventoryServiceNegativeTest() throws Exception {  	    	
//    	BuyItemDetails buyItemDetails = createBuyItemDetails();
//    	BuyGildedRoseInvenotryRequest request = new BuyGildedRoseInvenotryRequest(buyItemDetails);
//    	when(buyGlidedRoseRepository.save(any())).thenReturn(buyItemDetails);
//    	when(gildedRoseRepository.findAll()).thenReturn(createItemEntities());
//    	doThro
//    	BuyItemDetails buyItemDetailsResponse = service.buyGlideRoseInventory(request);       
//        assertNotNull(buyItemDetailsResponse);
//        assertEquals(buyItemDetails,buyItemDetailsResponse );           
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
}

