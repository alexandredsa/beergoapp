package br.com.beergo.provider;

import br.com.beergo.domain.dto.MapsLocation;

public interface OnLocationChanged {
    void result(MapsLocation location);
}
