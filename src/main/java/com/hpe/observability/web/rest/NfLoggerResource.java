package com.hpe.observability.web.rest;

import com.hpe.observability.service.LoggerService;
import io.opentracing.Scope;
import io.opentracing.Span;
import io.opentracing.SpanContext;
import io.opentracing.Tracer;
import io.opentracing.propagation.Format;
import io.opentracing.propagation.TextMapExtractAdapter;
import io.swagger.annotations.ApiOperation;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.eclipse.microprofile.metrics.MetricUnits;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Gauge;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.Timed;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.cloudfoundry.com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import static strman.Strman.toKebabCase;

//@RequestMapping("/")
@RestController
public class NfLoggerResource {
    private final List<String> animalNames;
    private Random random;
    OkHttpClient client = new OkHttpClient();
    @Autowired
    private Tracer tracer;

    @Value("${app.host}")
    private String host;

    @Autowired
    private LoggerService service;

    public NfLoggerResource()throws IOException {
        InputStream inputStream = new ClassPathResource("/animals.txt").getInputStream();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            animalNames = reader.lines().collect(Collectors.toList());
        }
        random = new Random();
    }

    @Timed(name = "helloWorldGet",
        description = "Monitor the time helloWorldGet Method takes",
        unit = MetricUnits.MILLISECONDS,
        absolute = true)
    @GET
    @GetMapping("/hello-world")
    @ApiOperation(value = "Monitor the time helloWorldGet Method takes")
    public ResponseEntity<JSONObject> helloWorldGet(@RequestHeader HttpHeaders headers) throws Exception {
        JSONObject response = new JSONObject();
        service.randomLog();
        Scope scope = tracer.buildSpan("generate-name").startActive(true);

        Span scientistSpan = tracer.buildSpan("scientist-name-service").asChildOf(scope.span()).start();
        String scientist = makeRequest("http://"+host+":9090/api/v1/scientists/random");
        scientistSpan.finish();

        SpanContext parentContext = tracer.extract(Format.Builtin.HTTP_HEADERS, new TextMapExtractAdapter(headers.toSingleValueMap()));
        Span span = tracer.buildSpan("find-random-animal-name").asChildOf(parentContext).start();
        String animalName = animalNames.get(random.nextInt(animalNames.size()));
        String name = toKebabCase(scientist) + "-" + toKebabCase(animalName);
        span.finish();
        response.put("response", name);

        return ResponseEntity.ok()
            .body(response);

    }

    @Metered(name = "helloWorldPut",
        unit = MetricUnits.MILLISECONDS,
        description = "Monitor the rate events occured",
        absolute = true)
    @PUT
    @PutMapping("/hello-world")
    @ApiOperation(value = "Monitor the rate events occured")
    public ResponseEntity<JSONObject> helloWorldPut()throws JsonProcessingException {
        JSONObject response = new JSONObject();
        response.put("response", "hello-world");
        service.randomLog();
        return ResponseEntity.ok()
            .body(response);
    }

    @Counted(unit = MetricUnits.NONE,
        name = "helloWorldPost",
        absolute = true,
        displayName = "hello get",
        description = "Monitor how many times helloWorldPost method was called")
    @POST
    @PostMapping("/hello-world")
    @ApiOperation(value = "Monitor how many times helloWorldPost method was called")
    public ResponseEntity<JSONObject> helloWorldPost()throws JsonProcessingException {
        JSONObject response = new JSONObject();
        response.put("response", "hello-world");
        service.randomLog();
        return ResponseEntity.ok()
            .body(response);
    }

    @GET
    @GetMapping("/get-sample-value")
    @Gauge(unit = MetricUnits.NONE, name = "getSampleValue", absolute = true)
    @ApiOperation(value = "Get an sample integer value")
    public Integer getSampleValue() throws JsonProcessingException{
        service.randomLog();
        return new Random().nextInt(Integer.MAX_VALUE);
    }

    private String makeRequest(String url) throws IOException {
        Request.Builder requestBuilder = new Request.Builder()
            .url(url);

        tracer.inject(
            tracer.activeSpan().context(),
            Format.Builtin.HTTP_HEADERS,
            new RequestBuilderCarrier(requestBuilder)

        );

        Request request = requestBuilder
            .build();

        try (okhttp3.Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
