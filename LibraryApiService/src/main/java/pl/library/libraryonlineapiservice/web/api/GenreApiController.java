package pl.library.libraryonlineapiservice.web.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pl.library.libraryonlineapiservice.domain.genre.GenreService;
import pl.library.libraryonlineapiservice.domain.genre.dto.GenreDto;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(
        path = "/api/genres",
        produces = {
                MediaType.APPLICATION_JSON_VALUE,
                MediaType.APPLICATION_XML_VALUE
        })
public class GenreApiController {
    private final GenreService genreService;

    public GenreApiController(GenreService genreService) {
        this.genreService = genreService;
    }

    @GetMapping("/{name}")
    public GenreDto getGenre(@PathVariable String name) {
        GenreDto genre = genreService.findGenreByName(name)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return genre;
    }

    @GetMapping
    public List<GenreDto> getGenreList() {
        return genreService.findAllGenres();
    }
    @PostMapping
    ResponseEntity<GenreDto> addGenre(@RequestBody GenreDto genreDto) {
        GenreDto addedGenre = genreService.addGenre(genreDto);
        URI savedJobOfferUri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(addedGenre.getId())
                .toUri();
        return ResponseEntity.created(savedJobOfferUri).body(addedGenre);
    }
}