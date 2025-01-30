package com.ucb.SIS213.Oasis.dto;

import com.ucb.SIS213.Oasis.entity.Auditoria;

import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class AuditoriaDTO {
    private Long idAudit;
    private String actividad;
    private Date fecha;
    private Time hora;
    private String fechaInicio;
    private String fechaFin;
    private String ip;
    private Integer adminId;
    private Integer clienteId;
    private String correo;

    public AuditoriaDTO(Auditoria auditoria, String correo) {
        this.idAudit = auditoria.getIdAudit();
        this.actividad = auditoria.getActividad();
        this.fecha = auditoria.getFecha();
        this.hora = auditoria.getHora();
        this.fechaInicio = formatDate(auditoria.getFechaInicio());
        this.fechaFin = formatDate(auditoria.getFechaFin());
        this.ip = auditoria.getIp();
        this.adminId = auditoria.getAdminId();
        this.clienteId = auditoria.getClienteId();
        this.correo = correo;
    }

    private String formatDate(Timestamp timestamp) {
        if (timestamp == null) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(timestamp);
    }

    // Getters y Setters

    public Long getIdAudit() {
        return idAudit;
    }

    public void setIdAudit(Long idAudit) {
        this.idAudit = idAudit;
    }

    public String getActividad() {
        return actividad;
    }

    public void setActividad(String actividad) {
        this.actividad = actividad;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public String getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(String fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public String getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(String fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Integer getClienteId() {
        return clienteId;
    }

    public void setClienteId(Integer clienteId) {
        this.clienteId = clienteId;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}