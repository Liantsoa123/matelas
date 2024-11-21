package com.example.matelas.dto.restesstockdto;


import com.example.matelas.model.achat.AchatMatierePremiere;
import com.example.matelas.model.achat.AchatMatierePremiereRepository;
import com.example.matelas.model.achat.AchatMatierePremiereService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class RestesStockService {

    private final AchatMatierePremiereRepository achatMatierePremiereRepository;
    private final AchatMatierePremiereService achatMatierePremiereService;

    @Autowired
    public RestesStockService(AchatMatierePremiereRepository achatMatierePremiereRepository, AchatMatierePremiereService achatMatierePremiereService) {
        this.achatMatierePremiereRepository = achatMatierePremiereRepository;
        this.achatMatierePremiereService = achatMatierePremiereService;
    }

    public List<RestesStockDTO> getRestesStockByDate(Date date , int matierePremiereId) {
        System.out.println("restesStockDTOList = "  +  matierePremiereId );
        System.out.println("restesStockDTOList = daty "  +  date.toString() );

        List<Object[]> results = achatMatierePremiereRepository.getRestesStockWithAchatMatierePremiere(date , matierePremiereId);
        List<RestesStockDTO> restesStockDTOList = new ArrayList<>();
        System.out.println("restesStockDTOList  " + results.size());
        for (Object[] result : results) {
            int id = (Integer) result[0];
            AchatMatierePremiere achatMatierePremiere = achatMatierePremiereService.getAchatMatierePremiereById((Integer) result[1]).get();
            Double quantiteReste = (Double) result[2];
            Date dateAchatMatierePremiere = (Date) result[3];
            RestesStockDTO restesStockDTO = new RestesStockDTO(id, achatMatierePremiere, quantiteReste, dateAchatMatierePremiere);
            restesStockDTOList.add(restesStockDTO);
            System.out.println("restesStockDTOList =  huhuhuhuhuhuhuhu ");
            System.out.println(quantiteReste);
        }

        return restesStockDTOList;
    }
}
