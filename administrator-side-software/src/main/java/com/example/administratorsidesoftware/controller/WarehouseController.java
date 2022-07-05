package com.example.administratorsidesoftware.controller;

import com.example.administratorsidesoftware.entity.Warehouse;
import com.example.administratorsidesoftware.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("warehouse/")
public class WarehouseController {
    @Autowired
    private WarehouseService warehouseService;

    /**
     * if there is a warehouse with the same warehouseId, update it; if there isn't, create it.
     * @param warehouse the warehouse should be updated or want to create.
     * @return true if the operation is successfully done.
     */
    @PostMapping
    public boolean saveOrUpdateWarehouse(@RequestBody Warehouse warehouse){
        return warehouseService.saveOrUpdate(warehouse);
    }

    /**
     * delete a warehouse by warehouse number.
     * @param warehouseNo number of the warehouse you want to delete
     * @return true if the operation is successfully done.
     */
    @DeleteMapping("/{warehouseNo}")
    public boolean delete(@PathVariable Integer warehouseNo){
        return warehouseService.removeById(warehouseNo);
    }


}
