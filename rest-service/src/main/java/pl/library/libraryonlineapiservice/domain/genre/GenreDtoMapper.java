package pl.library.libraryonlineapiservice.domain.genre;

import pl.library.libraryonlineapiservice.domain.genre.dto.GenreDto;

class GenreDtoMapper {
    static GenreDto map(Genre genre) {
        return new GenreDto(
                genre.getId(),
                genre.getName(),
                genre.getDescription()
        );
    }
}