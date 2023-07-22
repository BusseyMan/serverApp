package dbo;

import java.util.List;


public class ResponseHeader {
    private int numberOfElements;
    private int totalPages;
    private Integer page;
    private Integer max;
    private List<AuctionResponse> lines;

    public ResponseHeader(){}

    public ResponseHeader(int numberOfElements, int totalPages, Integer page, Integer max, List<AuctionResponse> lines) {
        this.numberOfElements = numberOfElements;
        this.totalPages = totalPages;
        this.page = page;
        this.max = max;
        this.lines = lines;
    }

  


    public int getNumberOfElements() {
        return numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public List<AuctionResponse> getLines() {
        return lines;
    }

    public void setLines(List<AuctionResponse> lines) {
        this.lines = lines;
    }

 
}

