package com.pol.springboot.app.librarydemo.services;

import com.pol.springboot.app.librarydemo.dto.alquiler.AlquilerCreateDTO;
import com.pol.springboot.app.librarydemo.exceptions.AlquilerNotFoundException;
import com.pol.springboot.app.librarydemo.exceptions.AlquilerYaCerradoException;
import com.pol.springboot.app.librarydemo.exceptions.ArticuloAlquiladoException;
import com.pol.springboot.app.librarydemo.mapper.AlquilerMapper;
import com.pol.springboot.app.librarydemo.model.Alquiler;
import com.pol.springboot.app.librarydemo.model.Articulo;
import com.pol.springboot.app.librarydemo.model.Usuario;
import com.pol.springboot.app.librarydemo.repository.AlquilerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AlquilerService {

    private final AlquilerRepository alquilerRepository;
    private final UsuarioService usuarioService;
    private final ArticuloService articuloService;

    public AlquilerService(
            AlquilerRepository alquilerRepository,
            UsuarioService usuarioService,
            ArticuloService articuloService
    ) {
        this.alquilerRepository = alquilerRepository;
        this.usuarioService = usuarioService;
        this.articuloService = articuloService;
    }

    public List<Alquiler> findAll() {
        return alquilerRepository.findAll();
    }

    public Alquiler findById(Long alquilerId) {
        return alquilerRepository.findById(alquilerId)
                .orElseThrow(() ->
                        new AlquilerNotFoundException("Alquiler no encontrado con ID: " + alquilerId));
    }

    @Transactional
    public Alquiler crearAlquiler(AlquilerCreateDTO dto) {

        Usuario usuario = usuarioService.findById(dto.getUsuarioId());
        Articulo articulo = articuloService.findById(dto.getArticuloId());

        if (articulo.isAlquilado()) {
            throw new ArticuloAlquiladoException("Artículo ya alquilado");
        }

        articulo.setAlquilado(true);
        articuloService.save(articulo);

        Alquiler alquiler = AlquilerMapper.toEntity(dto, usuario, articulo);
        return alquilerRepository.save(alquiler);
    }

    @Transactional
    public Alquiler devolverArticulo(Long alquilerId) {

        Alquiler alquiler = findById(alquilerId);

        if (!alquiler.isVigente()) {
            throw new AlquilerYaCerradoException("El alquiler ya está cerrado");
        }

        Articulo articulo = alquiler.getArticulo();

        // Liberar artículo
        articulo.setAlquilado(false);
        articuloService.save(articulo);

        // Cerrar alquiler
        alquiler.setVigente(false);
        alquiler.setFechaDevolucion(LocalDate.now());

        return alquilerRepository.save(alquiler);
    }

    public void deleteById(Long id) {
        alquilerRepository.deleteById(id);
    }
}
