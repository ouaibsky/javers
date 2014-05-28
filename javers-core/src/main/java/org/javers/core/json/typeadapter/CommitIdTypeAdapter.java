package org.javers.core.json.typeadapter;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import org.javers.common.exception.exceptions.JaversException;
import org.javers.common.exception.exceptions.JaversExceptionCode;
import org.javers.core.commit.CommitId;
import org.javers.core.json.JsonTypeAdapterTemplate;

public class CommitIdTypeAdapter extends JsonTypeAdapterTemplate<CommitId> {

    @Override
    public Class getValueType() {
        return CommitId.class;
    }

    @Override
    public CommitId fromJson(JsonElement json, JsonDeserializationContext jsonDeserializationContext) {
        String majorDotMinor = json.getAsString();

        String[] strings = majorDotMinor.split("\\.");

        if (strings.length != 2) {
            throw new JaversException(JaversExceptionCode.CANNOT_PARSE_COMMIT_ID, majorDotMinor);
        }

        long major = Long.parseLong(strings[0]);
        int minor = Integer.parseInt(strings[1]);

        return new CommitId(major, minor);
    }

    @Override
    public JsonElement toJson(CommitId commitId, JsonSerializationContext context) {
        return new JsonPrimitive(commitId.value());
    }
}
