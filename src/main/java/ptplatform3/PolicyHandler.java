package ptplatform3;

import ptplatform3.config.kafka.KafkaProcessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

//HNR-START
import java.util.List;
import java.util.Optional;
//HNR--END-

@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }

    //HNR-START
    @Autowired
    PtorderRepository ptorderRepository;
    //HNR--END-

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPtOrderConfirmed_PtOrderCompletionNotify(@Payload PtOrderConfirmed ptOrderConfirmed){

        // PT수강신청 확정 상태 이벤트 수신시 수강 확정 상태(ORDER_COMPLETED) 저장
        if(ptOrderConfirmed.isMe() && ptOrderConfirmed.getStatus() != null){
            System.out.println("##### listener PtOrderCompletionNotify : " + ptOrderConfirmed.toJson());
            //HNR-START
            Optional<Ptorder> ptorders = ptorderRepository.findById(ptOrderConfirmed.getPtOrderId());
            ptorders.get().setPtManagerId(ptOrderConfirmed.getId());
            ptorders.get().setPtTrainerId(ptOrderConfirmed.getPtTrainerId());
            ptorders.get().setStatus("ORDER_COMPLETED");
            ptorderRepository.save(ptorders.get());
            //HNR--END-
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPtResultCreated_PtResultCreationNotify(@Payload PtResultCreated ptResultCreated){

        // PT수업결과가 등록되면 결과확인 준비됨(RESULT_READY) 상태 저장
        if(ptResultCreated.isMe()){
            System.out.println("##### listener PtResultCreationNotify : " + ptResultCreated.toJson());
            //HNR-START
            Optional<Ptorder> ptorders = ptorderRepository.findById(ptResultCreated.getPtOrderId());
            ptorders.get().setStatus("RESULT_READY");
            // Ptorder ptorder= new Ptorder();
            // ptorders.setStatus("RESULT_READY");
            ptorderRepository.save(ptorders.get());
            //HNR--END-
        }
    }
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPtOrderCancelAccepted_PtOrderCancelAccept(@Payload PtOrderCancelAccepted ptOrderCancelAccepted){

        // PT수강신청 취소 수용됨 이벤트 수신시 수강 취소 완료 상태(ORDER_CANCELED) 저장
        if(ptOrderCancelAccepted.isMe() && ptOrderCancelAccepted.getStatus() != null){
            System.out.println("##### listener PtOrderCancelAccept : " + ptOrderCancelAccepted.toJson());
            //HNR-START
            Optional<Ptorder> ptorders = ptorderRepository.findById(ptOrderCancelAccepted.getPtOrderId());
            ptorders.get().setStatus("ORDER_CANCELED");
            ptorderRepository.save(ptorders.get());
            //HNR--END-
        }
    }

}
