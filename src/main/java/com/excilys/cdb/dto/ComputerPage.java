package com.excilys.cdb.dto;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import com.excilys.cdb.utils.ConfigurationUtils;

public class ComputerPage {
  private int currentPage;
  private int totalPages;
  // Number of all the computer that can be shown
  private int nbAllComputers;
  private List<ComputerDto> computerDtos;

  private int beginPage;
  private int endPage;

  // todo, to get it from configuration file
  private int currentMaxElementsPerPage = 10;
  private List<Integer> maxElementsPerPages;


  private static final String MAX_ELEM_PER_PAGE_KEY = "max_elements_per_page";
  private static final String CONFIG_VALUE_DELIMITER = ",";


  public void computeBeginEndPages() {

    String nbMaxElements = ConfigurationUtils.getConfiguration().getString(MAX_ELEM_PER_PAGE_KEY);
    String[] array = nbMaxElements.split(CONFIG_VALUE_DELIMITER);

    maxElementsPerPages = Arrays
        .stream(array)
        .map(String::trim)
        .map(Integer::new)
        .collect(Collectors.toList());

    int currentTens = currentPage / 10;
    boolean isCurrentFullTens = currentPage % 10 == 0;

    int totalTens = totalPages / 10;

    if (isCurrentFullTens) {
      beginPage = (currentTens - 1) * 10 + 1;
      endPage = (currentTens - 1) * 10 + 10;
    } else {

      beginPage = currentTens * 10 + 1;

      if (currentTens == totalTens) {
        endPage = totalPages;
      } else {
        endPage = currentTens * 10 + 10;
      }
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

  public int getNbAllComputers() {
    return nbAllComputers;
  }

  public void setNbAllComputers(int nbAllComputers) {
    this.nbAllComputers = nbAllComputers;
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

  public int getCurrentMaxElementsPerPage() {
    return currentMaxElementsPerPage;
  }

  public void setCurrentMaxElementsPerPage(int currentMaxElementsPerPage) {
    this.currentMaxElementsPerPage = currentMaxElementsPerPage;
  }

  public List<Integer> getMaxElementsPerPages() {
    return maxElementsPerPages;
  }

  public void setMaxElementsPerPages(List<Integer> maxElementsPerPages) {
    this.maxElementsPerPages = maxElementsPerPages;
  }
}
