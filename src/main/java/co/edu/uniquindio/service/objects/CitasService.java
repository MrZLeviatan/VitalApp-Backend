package co.edu.uniquindio.service.objects;

import co.edu.uniquindio.dto.cita.CambiarEstadoCitaDto;
import co.edu.uniquindio.dto.cita.CitaDto;
import co.edu.uniquindio.dto.cita.RegistrarCitaDto;
import co.edu.uniquindio.exceptions.ElementoNoEncontradoException;
import co.edu.uniquindio.models.objects.Cita;
import co.edu.uniquindio.models.objects.Formula;

import java.util.List;

public interface CitasService {
    
    // (PACIENTE)
    void registraCita(RegistrarCitaDto registrarCitaDto)
            throws ElementoNoEncontradoException;

    // (PACIENTE)
    void cancelarCita(CambiarEstadoCitaDto cambiarEstadoCitaDto)
            throws ElementoNoEncontradoException;

    // Volver la cita en estado EN_PROCESO (MÉDICO)
    void cambiarEstadoCita(CambiarEstadoCitaDto cambiarEstadoCitaDto)
            throws ElementoNoEncontradoException;

    // (PACIENTE, MEDICO)
    CitaDto obtenerCitaDtoId(Long id)
            throws ElementoNoEncontradoException;

    Cita obtenerCita(Long id)
            throws ElementoNoEncontradoException;

    // (PACIENTE)
    List<CitaDto> obtenerCitasPaciente(Long idPaciente)
            throws ElementoNoEncontradoException;

    // (PACIENTE)
    List<CitaDto> obtenerCitasPacientePendientes(Long idPaciente)
            throws ElementoNoEncontradoException;


    // (MEDICO,ADMIN)
    List<CitaDto> obtenerCitasMedico(Long idMedico)
            throws ElementoNoEncontradoException;


    void agregarFormulaCita(Cita cita, Formula formula);



}
