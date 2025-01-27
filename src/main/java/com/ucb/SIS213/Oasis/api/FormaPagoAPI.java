package com.ucb.SIS213.Oasis.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ucb.SIS213.Oasis.bl.FormaPagoBl;
import com.ucb.SIS213.Oasis.dto.ResponseDTO;
import com.ucb.SIS213.Oasis.entity.FormaPago;

import java.util.List;

@RestController
@CrossOrigin(origins = "*",maxAge = 3600)
@RequestMapping("/api/v1/formaPago")
public class FormaPagoAPI {

    private static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(FormaPagoAPI.class);

    FormaPagoBl formaPagoBl;

    @Autowired
    public FormaPagoAPI(FormaPagoBl formaPagoBl) {
        this.formaPagoBl = formaPagoBl;
    }

    // Endpoint para obtener todas las formas de pago
    @GetMapping
    public ResponseDTO getAllFormaPago() {
        List<FormaPago> formaPagoList;
        try{
            formaPagoList = formaPagoBl.getAllFormaPago();
            LOGGER.info("Formas de pago encontradas");
        } catch (RuntimeException e){
            LOGGER.error("Error al obtener las formas de pago");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(formaPagoList);
    }

    // Endpoint para obtener una forma de pago por su id
    @GetMapping("/{id}")
    public ResponseDTO getFormaPagoById(@PathVariable Long id) {
        FormaPago formaPago;
        try{
            formaPago = formaPagoBl.getFormaPagoById(id);
            LOGGER.info("Forma de pago encontrada");
        } catch (RuntimeException e){
            LOGGER.error("Error al obtener la forma de pago");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
        return new ResponseDTO(formaPago);
    }

    // Endpoint para crear una forma de pago
    @PostMapping("/create")
    public ResponseDTO createFormaPago(@RequestBody FormaPago formaPago) {
        FormaPago formaPagoCreada;
        try{
            formaPagoCreada = formaPagoBl.createFormaPago(formaPago);
            LOGGER.info("Forma de pago creada");
            return new ResponseDTO(formaPagoCreada);
        } catch (RuntimeException e){
            LOGGER.error("Error al crear la forma de pago");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }

    // Endpoint para modificar una forma de pago por su id
    @PutMapping("/update/{id}")
    public ResponseDTO updateFormaPago(@PathVariable Long id, @RequestBody FormaPago formaPago) {
        FormaPago formaPagoActualizada;
        try{
            formaPagoActualizada = formaPagoBl.updateFormaPagoById(id, formaPago);
            LOGGER.info("Forma de pago actualizada");
            return new ResponseDTO(formaPagoActualizada);
        } catch (RuntimeException e){
            LOGGER.error("Error al actualizar la forma de pago");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }

    // Endpoint para eliminar una forma de pago
    @DeleteMapping("/delete/{id}")
    public ResponseDTO deleteFormaPago(@PathVariable Long id) {
        try{
            formaPagoBl.deleteFormaPagoById(id);
            LOGGER.info("Forma de pago eliminada");
            return new ResponseDTO("Forma de pago eliminada");
        } catch (RuntimeException e){
            LOGGER.error("Error al eliminar la forma de pago");
            return new ResponseDTO("TASK-1000", e.getMessage());
        }
    }

}
