package com.example.matelas.model.restesstockdto;


import com.example.matelas.model.achat.AchatMatierePremiere;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class RestesStockService {

    private final RestesStockRepository restesStockRepository;

    @Autowired
    public RestesStockService(RestesStockRepository restesStockRepository) {
        this.restesStockRepository = restesStockRepository;
    }

    public List<RestesStockDTO> getRestesStockByDate(Date date) {
        List<Object[]> results = restesStockRepository.getRestesStockWithAchatMatierePremiere(date);
        List<RestesStockDTO> restesStockDTOList = new ArrayList<>();

        for (Object[] result : results) {
            int id = (Integer) result[0];
            AchatMatierePremiere achatMatierePremiere = (AchatMatierePremiere) result[1];
            double quantiteReste = (Double) result[2];
            Date dateAchatMatierePremiere = (Date) result[3];

            RestesStockDTO restesStockDTO = new RestesStockDTO(id, achatMatierePremiere, quantiteReste, dateAchatMatierePremiere);
            restesStockDTOList.add(restesStockDTO);
        }

        return restesStockDTOList;
    }
}
