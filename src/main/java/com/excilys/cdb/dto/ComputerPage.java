package com.excilys.cdb.dto;

import java.util.List;

public class ComputerPage {
  private int currentPage;
  private int totalPages;
  // Numbe of all the computer that can be shown
  private int nbAllComptuers;
  private List<ComputerDto> computerDtos;

  private int beginPage;
  private int endPage;

  public void computeBeginEndPages() {
    int currentTens = currentPage / 10;
    boolean isCurrentFullTens = currentPage % 10 == 0;
    int totalTens = totalPages / 10;
    int residu = currentTens % 10;

    if (isCurrentFullTens) {
      beginPage = (currentTens - 1) * 10 + 1;
    } else {
      beginPage = currentTens * 10 + 1;
      endPage = currentTens * 10 + 10;
      return;
    }

    if (currentTens < totalTens) {
      endPage = currentTens * 10 + 10;
    } else {
      endPage = currentTens * 10 + residu;
    }
  }

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

  public List<ComputerDto> getComputerDtos() {
    return computerDtos;
  }

  public void setComputerDtos(List<ComputerDto> computerDtos) {
    this.computerDtos = computerDtos;
  }

  public int getBeginPage() {
    return beginPage;
  }

  public void setBeginPage(int beginPage) {
    this.beginPage = beginPage;
  }

  public int getEndPage() {
    return endPage;
  }

  public void setEndPage(int endPage) {
    this.endPage = endPage;
  }
}
