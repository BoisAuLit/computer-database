package com.excilys.cdb.dto;

import java.util.List;

public class ComputerPage {
  private int currentPage;
  private int totalPages;
  // Numbe of all the computer that can be shown
  private int nbAllComptuers;
  private List<ComputerDto> computers;

  public int getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(int currentPage) {
    this.currentPage = currentPage;
  }

  public int getTotalPages() {
    return totalPages;
  }

  public void setTotalPages(int totalPages) {
    this.totalPages = totalPages;
  }

  public int getNbAllComptuers() {
    return nbAllComptuers;
  }

  public void setNbAllComptuers(int nbAllComptuers) {
    this.nbAllComptuers = nbAllComptuers;
  }

  public List<ComputerDto> getComputers() {
    return computers;
  }

  public void setComputers(List<ComputerDto> computers) {
    this.computers = computers;
  }
}
