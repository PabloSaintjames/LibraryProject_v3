package com.pol.springboot.app.librarydemo.services;

import com.pol.springboot.app.librarydemo.exceptions.AlquilerNotFoundException;
import com.pol.springboot.app.librarydemo.exceptions.AlquilerYaCerradoException;
import com.pol.springboot.app.librarydemo.exceptions.ArticuloAlquiladoException;
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
    public Alquiler guardarAlquiler(Alquiler alquiler) {

        // 🔹 Ya NO devuelve Optional
        Usuario usuario = usuarioService.findById(alquiler.getUsuario().getId());

        Articulo articulo = articuloService.findById(alquiler.getArticulo().getId());

        if (articulo.isAlquilado()) {
            throw new ArticuloAlquiladoException("El artículo ya está alquilado");
        }

        // Marcar artículo como alquilado
        articulo.setAlquilado(true);
        articuloService.save(articulo);

        // Crear alquiler
        alquiler.setUsuario(usuario);
        alquiler.setArticulo(articulo);
        alquiler.setFechaAlquiler(LocalDate.now());
        alquiler.setVigente(true);

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
