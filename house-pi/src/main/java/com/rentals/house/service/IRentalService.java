package com.rentals.house.service;

import com.rentals.house.model.Rental;

import java.util.List;
import java.util.Optional;

public interface IRentalService {

    /**
     * Récupère la liste de toutes les locations.
     * @return Liste des locations.
     */
    List<Rental> getAllRentals();

    /**
     * Récupère une location par son identifiant.
     * @param id Identifiant de la location.
     * @return Un Optional contenant la location si trouvée.
     */
    Optional<Rental> getRentalById(Long id);

    /**
     * Enregistre ou met à jour une location.
     * @param rental Objet Rental à sauvegarder.
     * @return L'objet Rental enregistré.
     */
    Rental saveRental(Rental rental);

    /**
     * Supprime une location par son identifiant.
     * @param id Identifiant de la location à supprimer.
     */
    void deleteRental(Long id);
}
