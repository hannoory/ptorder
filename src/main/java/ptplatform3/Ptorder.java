package ptplatform3;

import javax.persistence.*;
import org.springframework.beans.BeanUtils;
import java.util.List;

@Entity
@Table(name="Ptorder_table")
public class Ptorder {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long ptManagerId;
    private Long ptTrainerId;
    private String status;
    private Long ptClassId;
    private String ptClassName;
    private Long customerId;
    private String customerName;

    @PostPersist
    public void onPostPersist(){
//        if(this.getStatus().equals("ORDERED")) {
            System.out.println("[HNR_DEBUG] ###########################");
            System.out.println("[HNR_DEBUG] ######### ORDERED #########");
            System.out.println("[HNR_DEBUG] ###########################");
            PtOrdered ptOrdered = new PtOrdered();
            BeanUtils.copyProperties(this, ptOrdered);
            ptOrdered.publishAfterCommit();
//        }
    }

    @PostUpdate
    public void onPostUpdate(){
        if(this.getStatus().equals("ORDER_COMPLETED")) {
            System.out.println("[HNR_DEBUG] ###################################");
            System.out.println("[HNR_DEBUG] ######### ORDER_COMPLETED #########");
            System.out.println("[HNR_DEBUG] ###################################");
            PtOrderCompleted ptOrderCompleted = new PtOrderCompleted();
            BeanUtils.copyProperties(this, ptOrderCompleted);
            ptOrderCompleted.publishAfterCommit();
        } else if(this.getStatus().equals("RESULT_READY")) {
            System.out.println("[HNR_DEBUG] ################################");
            System.out.println("[HNR_DEBUG] ######### RESULT_READY #########");
            System.out.println("[HNR_DEBUG] ################################");
            PtResultRead ptResultRead = new PtResultRead();
            BeanUtils.copyProperties(this, ptResultRead);
            ptResultRead.publishAfterCommit();
        } else if(this.getStatus().equals("CANCEL_ORDERED")) {
            System.out.println("[HNR_DEBUG] ##################################");
            System.out.println("[HNR_DEBUG] ######### CANCEL_ORDERED #########");
            System.out.println("[HNR_DEBUG] ##################################");
            PtCancelOrdered ptCancelOrdered = new PtCancelOrdered();
            BeanUtils.copyProperties(this, ptCancelOrdered);
            ptCancelOrdered.publishAfterCommit();
        } else if(this.getStatus().equals("ORDER_CANCELED")) {
            System.out.println("[HNR_DEBUG] ##################################");
            System.out.println("[HNR_DEBUG] ######### ORDER_CANCELED #########");
            System.out.println("[HNR_DEBUG] ##################################");
            PtOrderCanceled ptOrderCanceled = new PtOrderCanceled();
            BeanUtils.copyProperties(this, ptOrderCanceled);
            ptOrderCanceled.publishAfterCommit();
        }
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getPtManagerId() {
        return ptManagerId;
    }

    public void setPtManagerId(Long ptManagerId) {
        this.ptManagerId = ptManagerId;
    }
    public Long getPtTrainerId() {
        return ptTrainerId;
    }

    public void setPtTrainerId(Long ptTrainerId) {
        this.ptTrainerId = ptTrainerId;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public Long getPtClassId() {
        return ptClassId;
    }

    public void setPtClassId(Long ptClassId) {
        this.ptClassId = ptClassId;
    }
    public String getPtClassName() {
        return ptClassName;
    }

    public void setPtClassName(String ptClassName) {
        this.ptClassName = ptClassName;
    }
    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }
    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }




}
