package com.hibernate.api;

import com.hibernate.entity.Car;
import com.hibernate.entity.Lot;
import com.hibernate.repository.CarRepository;
import com.hibernate.repository.LotRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class representing the API for interacting with car lots.
 */
public class CarStoreAPI {
    private final LotRepository lotRepository;
    private final CarRepository carRepository;

    private final List<Long> lotsIDsToDelete;
    private List<Lot> lots;

    public CarStoreAPI() {
        lotRepository = new LotRepository();
        carRepository = new CarRepository();
        lots = new ArrayList<>();
        lotsIDsToDelete = new ArrayList<>();
    }

    public List<Lot> getLots() {
        return lots;
    }

    public void setLots(List<Lot> lots) {
        this.lots = lots;
    }

    public boolean isEmpty() {
        return lots.isEmpty();
    }

    public void addLot(Lot lot) {
        lots.add(lot);
    }

    public Lot get(int index) {
        return lots.get(index);
    }

    public void removeLot(Lot lot) {
        addLotToDelete(lot.getLotId());
        lots.remove(lot);
    }

    public int size() {
        return lots.size();
    }

    /**
     * Loads all lots from the repository and updates the internal list of lots.
     *
     * @return The list of lots loaded from the repository.
     */
    public List<Lot> loadAllLots() {
        lots = lotRepository.getAll();
        return lots;
    }

    /**
     * Loads all lots from the repository with transferred brand and updates the internal list of lots.
     *
     * @param brand The car brand to load.
     * @return The list of lots loaded from the repository.
     */
    public List<Lot> loadAllByBrand(String brand) {
        lots = lotRepository.getByCarBrand(brand);
        return lots;
    }

    /**
     * Loads all lots from the repository with price value from min and max and updates the internal
     * list of lots.
     *
     * @param min The minimum price value.
     * @param max The maximum price value.
     * @return The list of lots loaded from the repository.
     */
    public List<Lot> loadByPriceBetween(double min, double max) {
        lots = lotRepository.getByPriceBetween(min, max);
        return lots;
    }

    /**
     * Loads all lots from the repository with transferred brand, and price value from min and max.
     * Updates the internal list of lots.
     *
     * @param brand The car brand to load.
     * @param min   The minimum price value.
     * @param max   The maximum price value.
     * @return The list of lots loaded from the repository.
     */
    public List<Lot> loadByBrandAndPriceBetween(String brand, double min, double max) {
        lots = lotRepository.getByBrandAndPriceBetween(brand, min, max);
        return lots;
    }

    /**
     * Retrieves all cars that do not have lots.
     *
     * @return The list of cars retrieved from the repository without lots.
     */
    public List<Car> getCarsWithoutLots() {
        return carRepository.findCarsWithoutLots();
    }

    /**
     * Pushes the changes made to the lots to the repository.
     * Deletes lots marked for deletion and saves or updates the remaining lots.
     */
    public void push() {
        lotRepository.deleteByIdsList(lotsIDsToDelete);
        lotsIDsToDelete.clear();
        carRepository.saveCars(
                lots.stream()
                        .filter(lot -> lot.getCar().getId() == null)
                        .map(Lot::getCar)
                        .collect(Collectors.toList())
        );
        lotRepository.saveLots(
                lots.stream()
                        .filter(lot -> lot.getLotId() == null)
                        .collect(Collectors.toList())
        );
        lotRepository.updateLots(lots);
    }
    /**
     * Clears the detached cars from the database. Detached cars are those cars that are not associated with any lot.
     */
    public void clearDetachedCars(){
        carRepository.deleteCars(
                getCarsWithoutLots());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (var lot : lots) {
            sb.append(lot).append("\n\n");
        }
        return sb.toString();
    }

    /**
     * Adds the lot ID to the list for deletion.
     * @param id Lot ID to be deleted.
     */
    private void addLotToDelete(Long id) {
        lotsIDsToDelete.add(id);
    }
}
