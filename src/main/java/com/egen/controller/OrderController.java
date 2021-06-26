package com.egen.controller;

import com.egen.model.dto.OrderDto;
import com.egen.model.entity.Order;
import com.egen.response.Response;
import com.egen.response.StatusMessage;
import com.egen.response.ResponseMetaData;
import com.egen.service.CustomerService;
import com.egen.service.OrderItemService;
import com.egen.service.OrderService;
import com.egen.service.kafka.OrderProducerServiceImpl;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@RequestMapping(value = "/orders")
@Api("Order related endpoints")
@Slf4j
public class OrderController {

    @Autowired
    CustomerService customerService;
    @Autowired
    OrderService ordersService;
    @Autowired
    OrderItemService itemService;

    @Autowired
    OrderProducerServiceImpl producerService;

    @GetMapping(produces = "application/json")
    @ApiOperation(value = "Fetches all the orders",
            notes = "Returns all the orders")
    @ApiResponses(value={
            @ApiResponse(code=302,message = "FOUND"),
            @ApiResponse(code=500,message = "Interval Server Error"),
            @ApiResponse(code=200,message = "OK")
    })
    public Response<List<Order>> getAllOrders(){
        return Response.<List<Order>>builder()
                .meta(ResponseMetaData.builder().statusCode(302)
                        .statusMessage(StatusMessage.FOUND.name())
                        .build()).data((ordersService.getAllOrders()))
                .build();
    }

    @PostMapping(value = "/publish/order")
    @ApiOperation(value = "Creates a new order",
            notes = "Refer the Order DTO class to know the Json format")
    @ApiResponses(value={
            @ApiResponse(code=201,message = "CREATED"),
            @ApiResponse(code=500,message = "INTERNAL SERVER ERROR"),
            @ApiResponse(code=200,message = "OK")
    })
    public String publishOrder(@RequestBody OrderDto orderDto){
        log.info("Order Received in Order Controller:{}",orderDto);
        producerService.sendOrder(orderDto);
        return "Order Received";
    }

    @GetMapping(produces = "application/json",value = "/pagelimit")
    @ApiOperation(value = "Fetches all the orders by limiting 15 orders per page by default",
            notes = "Returns 15 orders in one page and can be changed to other values as well")
    @ApiResponses(value={
            @ApiResponse(code=302,message = "FOUND"),
            @ApiResponse(code=500,message = "Interval Server Error"),
            @ApiResponse(code=200,message = "OK")
    })
    public Response<List<Order>> getAllOrdersByPageLimit(@RequestParam(defaultValue = "0")Integer pageNo,
                                                          @RequestParam(defaultValue = "15")Integer pageSize){
        return Response.<List<Order>>builder()
                .meta(ResponseMetaData.builder().statusCode(302)
                        .statusMessage(StatusMessage.FOUND.name())
                        .build()).data((ordersService.findAllByPageLimit(pageNo,pageSize)))
                .build();
    }

    @GetMapping(produces = "application/json",value = "/{id}")
    @ApiOperation(value = "Fetches the order by the given Id",
            notes = "Returns details of a signle order")
    @ApiResponses(value={
            @ApiResponse(code=302,message = "FOUND"),
            @ApiResponse(code=404,message = "Not Found"),
            @ApiResponse(code=200,message = "OK")
    })
    public Response<Order> getOrderById(@ApiParam(value = "Order id of the order",required = true)
                                         @PathVariable("id")Long id){
        return Response.<Order>builder()
                .meta(ResponseMetaData.builder().statusCode(302)
                        .statusMessage(StatusMessage.FOUND.name()).build()).data((ordersService.getOrderById(id.toString()))).build();
    }

    @GetMapping(value = "/interval",produces = "application/json")
    @ApiOperation(value = "Fetches the order within the given interval of time",
            notes = "Specify the start and end time in Timestamp format")
    @ApiResponses(value={
            @ApiResponse(code=302,message = "FOUND"),
            @ApiResponse(code=404,message = "Not Found"),
            @ApiResponse(code=200,message = "OK")
    })
    public Response<List<Order>> getAllOrdersWithInInterval(@RequestParam(name = "startTime") Timestamp startTime,
                                                             @RequestParam(name = "endTime") Timestamp endTime){
        return Response.<List<Order>>builder()
                .meta(ResponseMetaData.builder().statusCode(302)
                        .statusMessage(StatusMessage.FOUND.name())
                        .build()).data((ordersService.getOrdersWithTimeInterval(startTime, endTime)))
                .build();
    }

    @GetMapping(value = "zipcode/{zip}",produces = "application/json")
    @ApiOperation(value = "Fetches list of orders having the according to highest billing amount in a particular area",
            notes = "Provide the zipcode to fetch the details")
    @ApiResponses(value={
            @ApiResponse(code=302,message = "FOUND"),
            @ApiResponse(code=404,message = "Not Found"),
            @ApiResponse(code=200,message = "OK")
    })
    public Response<List<Order>> top10OrdersWithHighestDollarAmountInZip(@PathVariable("zip") String zip){
        return Response.<List<Order>>builder()
                .meta(ResponseMetaData.builder().statusCode(302)
                        .statusMessage(StatusMessage.FOUND.name())
                        .build()).data((ordersService.top10OrdersWithHighestDollarAmountInZip(zip)))
                .build();
    }

    @PostMapping(value = "/placeorder",consumes = "application/json",produces = "application/json")
    @ApiOperation(value = "Creates a new order",
            notes = "Refer the Order DTO class to know the Json format")
    @ApiResponses(value={
            @ApiResponse(code=201,message = "CREATED"),
            @ApiResponse(code=500,message = "INTERNAL SERVER ERROR"),
            @ApiResponse(code=200,message = "OK")
    })
    public Response<String> placeOrder(@RequestBody OrderDto orderDto){
        return ordersService.placeOrder(orderDto) == Boolean.TRUE ? Response.<String>builder()
                .meta(ResponseMetaData.builder().statusCode(201)
                        .statusMessage(StatusMessage.CREATED.name())
                        .build())
                .data("Order placed successfully")
                .build()
                :
                Response.<String>builder()
                        .meta(ResponseMetaData.builder().statusCode(500)
                                .statusMessage(StatusMessage.UNKNOWN_INTERNAL_ERROR.name())
                                .build())
                        .data("Failed to place order")
                        .build();
    }

    /**
     * This method will cancel the selected order
     * @param id - Order id
     * @return
     */
    @PutMapping(value = "/cancel/{id}",produces = "application/json")
    @ApiOperation(value = "Cancels the order",
            notes = "Changes the status to cancelled")
    @ApiResponses(value={
            @ApiResponse(code=500,message = "INTERNAL SERVER ERROR"),
            @ApiResponse(code=200,message = "OK")
    })
    public Response<Order>  cancelOrder(@PathVariable("id")Long id){
        return Response.<Order>builder()
                .meta(ResponseMetaData.builder().statusCode(200)
                        .statusMessage(StatusMessage.SUCCESS.name())
                        .build())
                .data((ordersService.cancelOrder(id.toString())))
                .build();
    }

    /**
     * Updates the selected order by changing the values desired
     * Currently this method is used to change the order status to delivered
     * @param id - order id
     * @return
     */
    @PutMapping(value ="/update-order/{id}",produces = "application/json",consumes = "application/json")
    @ApiOperation(value = "Changes the status of the order based on user input",
            notes = "Changes the status to desired value")
    @ApiResponses(value={
            @ApiResponse(code=500,message = "INTERNAL SERVER ERROR"),
            @ApiResponse(code=200,message = "OK")
    })
    public Response<Order> updateOrder(@RequestBody Order order, @PathVariable("id")Long id){
        return Response.<Order>builder()
                .meta(ResponseMetaData.builder().statusCode(200)
                        .statusMessage(StatusMessage.SUCCESS.name())
                        .build())
                .data((ordersService.updateOrder(id.toString(), order)))
                .build();
    }

    @GetMapping(produces = "application/json",value = "/sortby")
    @ApiOperation(value = "Sorts the value according to the value selected by the user",
            notes = "Provide the value which you want to sort")
    @ApiResponses(value={
            @ApiResponse(code=500,message = "INTERNAL SERVER ERROR"),
            @ApiResponse(code=200,message = "OK")
    })
    public Response<List<Order>> getSortedValues(@RequestParam(defaultValue = "0")Integer pageNo,
                                                  @RequestParam(defaultValue = "15")Integer pageSize,
                                                  @RequestParam(defaultValue = "itemQuantity")String sortBy){
        return Response.<List<Order>>builder()
                .meta(ResponseMetaData.builder().statusCode(302)
                        .statusMessage(StatusMessage.FOUND.name())
                        .build()).data((ordersService.sortByValues(pageNo,pageSize,sortBy)))
                .build();
    }
}
