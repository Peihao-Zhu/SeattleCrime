package com.example.seattledashboard;

import com.example.seattledashboard.dao.BeatDao;
import com.example.seattledashboard.dao.McppDao;
import com.example.seattledashboard.dao.PrecinctDao;
import com.example.seattledashboard.dao.SectorDao;
import com.example.seattledashboard.model.Beat;
import com.example.seattledashboard.model.Mcpp;
import com.example.seattledashboard.model.Precinct;
import com.example.seattledashboard.model.Sector;
import java.sql.SQLException;

public class Test {



  public static void main(String[] args)  throws SQLException {

    McppDao mcppDao = McppDao.getInstance();
    SectorDao sectorDao = SectorDao.getInstance();
    PrecinctDao precinctDao = PrecinctDao.getInstance();
    BeatDao beatDao = BeatDao.getInstance();

    // creat
    Mcpp mcpp1 = Mcpp.builder().mcppName("mcpp").build();
    mcppDao.create(mcpp1);
    Sector sector1 = Sector.builder().sectorName("sector").build();
    sectorDao.create(sector1);
    Precinct precinct1 = Precinct.builder().precinctName("precinct").build();
    precinctDao.create(precinct1);
    Beat beat1 = Beat.builder().beatName("beat").build();
    beatDao.create(beat1);

    // query
    Mcpp mcpp = mcppDao.getMcppById(mcpp1.getMcppId());
    System.out.println(mcpp.toString());
    Sector sector = sectorDao.getSectorById(sector1.getSectorId());
    System.out.println(sector.toString());
    Precinct precinct = precinctDao.getPrecinctById(precinct1.getPrecinctId());
    System.out.println(precinct.toString());
    Beat beat = beatDao.getBeatById(beat1.getBeatId());
    System.out.println(beat.toString());

    // update
    mcpp = mcppDao.updateName(mcpp, "mcpp1");
    System.out.println(mcpp.toString());
    sector = sectorDao.updateName(sector, "sector1");
    System.out.println(sector.toString());
    precinct = precinctDao.updateName(precinct, "precinct1");
    System.out.println(precinct.toString());
    beat = beatDao.updateName(beat, "beat1");
    System.out.println(beat.toString());

    // delete
    mcppDao.delete(mcpp);
    sectorDao.delete(sector);
    precinctDao.delete(precinct);
    beatDao.delete(beat);

  }
}
