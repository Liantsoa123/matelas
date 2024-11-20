package com.example.matelas.dto.restesstockdto;


import com.example.matelas.model.achat.AchatMatierePremiere;
import com.example.matelas.model.achat.AchatMatierePremiereRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class RestesStockService {

    private final AchatMatierePremiereRepository achatMatierePremiereRepository;

    @Autowired
    public RestesStockService( AchatMatierePremiereRepository achatMatierePremiereRepository) {
        this.achatMatierePremiereRepository = achatMatierePremiereRepository;
    }

    public List<RestesStockDTO> getRestesStockByDate(Date date , int matierePremiereId) {
        List<Object[]> results = achatMatierePremiereRepository.getRestesStockWithAchatMatierePremiere(date , matierePremiereId);
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
