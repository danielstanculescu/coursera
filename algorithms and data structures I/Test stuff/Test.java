private int map2Dto1DStartingFrom1(int row, int col, int n) {
        return (row-1) * n + col;
    }


private int map2Dto1DStartingFrom0(int row, int col, int n) {
        return map2Dto1DStartingFrom1(row, col-1, n);
    }

    private int map1DtoRow(int number, int n) {
        return number/n + 1;
    }

    private int map1DtoCol(int number, int n) {
        return number % n + 1;
    }    

// return a new array with only blocked sites
private int[] blockedSites(Percolation p, int n) {
        int[] blockedSites;
        int number = 0, r, c, j;
        for (int k = 0; k < n*n; k++) {
            r = map1DtoRow(k, n);
            c = map1DtoCol(k, n);
            if (!p.isOpen(r, c))
                number++;
        }
        blockedSites = new int [number];
        j = 0;
        for (int k = 0; k < n*n; k++) {
            r = map1DtoRow(k, n);
            c = map1DtoCol(k, n);
            if (!p.isOpen(r, c)) {
                blockedSites[j] = map2Dto1DStartingFrom0(r, c, n);
                j++;
            }
        }
        return blockedSites;
    }

private testByte(){
    byte a=5;
    byte b=7;
    byte resultOR = (byte)(a|b);

    System.out.println(a|b);
    System.out.println(resultOR);
}