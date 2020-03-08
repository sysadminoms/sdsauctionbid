package com.oms.sdsauctionbid.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.oms.sdsauctionbid.utils.LocalDateDeserializer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.joda.time.DateTime;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
public class LiveAuction implements Serializable {

    public double adminBoughtForward;
    public double bidSize;
    public double lotValue;

    public int oneUp;
    public int twoUp;;
    public int threeUp;
    public int fourUp;
    public int fiveUp;
    public int sixUp;
    public int sevenUp;
    public int eightUp;
    public int nineUp;
    public int tenUp;

    public int oneDown;
    public int twoDown;
    public int threeDown;
    public int fourDown;
    public int fiveDown;
    public int sixDown;
    public int sevenDown;
    public int eightDown;
    public int nineDown;
    public int tenDown;
    @JsonDeserialize(using= LocalDateDeserializer.class)
    public DateTime oneUpDate;
    @JsonDeserialize(using= LocalDateDeserializer.class)
    public DateTime twoUpDate;
    @JsonDeserialize(using= LocalDateDeserializer.class)
    public DateTime threeUpDate;
    @JsonDeserialize(using= LocalDateDeserializer.class)
    public DateTime fourUpDate;
    @JsonDeserialize(using= LocalDateDeserializer.class)
    public DateTime fiveUpDate;
    @JsonDeserialize(using= LocalDateDeserializer.class)
    public DateTime sixUpDate;
    @JsonDeserialize(using= LocalDateDeserializer.class)
    public DateTime sevenUpDate;
    @JsonDeserialize(using= LocalDateDeserializer.class)
    public DateTime eightUpDate;
    @JsonDeserialize(using= LocalDateDeserializer.class)
    public DateTime nineUpDate;
    @JsonDeserialize(using= LocalDateDeserializer.class)
    public DateTime tenUpDate;
    @JsonDeserialize(using= LocalDateDeserializer.class)
    public DateTime oneDownDate;
    @JsonDeserialize(using= LocalDateDeserializer.class)
    public DateTime twoDownDate;
    @JsonDeserialize(using= LocalDateDeserializer.class)
    public DateTime threeDownDate;
    @JsonDeserialize(using= LocalDateDeserializer.class)
    public DateTime fourDownDate;
    @JsonDeserialize(using= LocalDateDeserializer.class)
    public DateTime fiveDownDate;
    @JsonDeserialize(using= LocalDateDeserializer.class)
    public DateTime sixDownDate;
    @JsonDeserialize(using= LocalDateDeserializer.class)
    public DateTime sevenDownDate;
    @JsonDeserialize(using= LocalDateDeserializer.class)
    public DateTime eightDownDate;
    @JsonDeserialize(using= LocalDateDeserializer.class)
    public DateTime nineDownDate;
    @JsonDeserialize(using= LocalDateDeserializer.class)
    public DateTime tenDownDate;

    public double commission;
}
