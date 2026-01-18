package com.pol.springboot.app.librarydemo.services;

import com.pol.springboot.app.librarydemo.dto.alquiler.AlquilerCreateDTO;
import com.pol.springboot.app.librarydemo.exceptions.AccesoDenegadoException;
import com.pol.springboot.app.librarydemo.exceptions.AlquilerNotFoundException;
import com.pol.springboot.app.librarydemo.exceptions.AlquilerYaCerradoException;
import com.pol.springboot.app.librarydemo.exceptions.ArticuloAlquiladoException;
import com.pol.springboot.app.librarydemo.mapper.AlquilerMapper;
import com.pol.springboot.app.librarydemo.model.Alquiler;
import com.pol.springboot.app.librarydemo.model.Articulo;
import com.pol.springboot.app.librarydemo.model.Rol;
import com.pol.springboot.app.librarydemo.model.Usuario;
import com.pol.springboot.app.librarydemo.repository.AlquilerRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
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

    /* ======================
       CONSULTAS
       ====================== */

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','OPERARIO')")
    public List<Alquiler> findAll() {
        return alquilerRepository.findAll();
    }

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','OPERARIO','USUARIO')")
    public Alquiler findById(Long id) {
        return alquilerRepository.findById(id)
                .orElseThrow(() ->
                        new AlquilerNotFoundException(
                                "Alquiler no encontrado con ID: " + id
                        )
                );
    }

    /* ======================
       CREAR ALQUILER
       ====================== */

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','OPERARIO','USUARIO')")
    @Transactional
    public Alquiler crearAlquiler(AlquilerCreateDTO dto) {

        Usuario usuario = usuarioService.findById(dto.getUsuarioId());
        validarRolParaAlquilar(usuario);

        Articulo articulo = articuloService.findById(dto.getArticuloId());

        if (articulo.isAlquilado()) {
            throw new ArticuloAlquiladoException("Artículo ya alquilado");
        }

        articulo.setAlquilado(true);
        articuloService.save(articulo);

        return alquilerRepository.save(
                AlquilerMapper.toEntity(dto, usuario, articulo)
        );
    }

    private void validarRolParaAlquilar(Usuario usuario) {
        if (usuario.getRol().getTipo() == Rol.TipoRol.INVITADO) {
            throw new AccesoDenegadoException(
                    "Los invitados no pueden alquilar"
            );
        }
    }

    /* ======================
       DEVOLVER ALQUILER
       ====================== */

    @PreAuthorize("hasAnyRole('ADMINISTRADOR','OPERARIO','USUARIO')")
    @Transactional
    public Alquiler devolverArticulo(Long alquilerId) {

        Alquiler alquiler = findById(alquilerId);

        if (!alquiler.isVigente()) {
            throw new AlquilerYaCerradoException(
                    "El alquiler ya está cerrado"
            );
        }

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        Usuario actual = usuarioService.findByEmail(email);

        boolean esAdminOOperario =
                actual.getRol().getTipo() == Rol.TipoRol.ADMINISTRADOR ||
                        actual.getRol().getTipo() == Rol.TipoRol.OPERARIO;

        boolean esElQueAlquilo =
                alquiler.getUsuario().getId().equals(actual.getId());

        if (!esAdminOOperario && !esElQueAlquilo) {
            throw new AccesoDenegadoException(
                    "No puedes devolver este alquiler"
            );
        }

        Articulo articulo = alquiler.getArticulo();
        articulo.setAlquilado(false);
        articuloService.save(articulo);

        alquiler.setVigente(false);
        alquiler.setFechaDevolucion(LocalDate.now());

        return alquilerRepository.save(alquiler);
    }
}