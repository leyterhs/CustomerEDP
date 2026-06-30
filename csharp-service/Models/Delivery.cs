using System.Text.Json.Serialization;

namespace csharp_service.Models;

public class Delivery
{
    [JsonPropertyName("id")]
    public int Id { get; set; }

    [JsonPropertyName("title")]
    public string Title { get; set; } = string.Empty;

    [JsonPropertyName("description")]
    public string? Description { get; set; }

    [JsonPropertyName("engagement_id")]
    public int EngagementId { get; set; }

    [JsonPropertyName("assigned_to")]
    public int? AssignedTo { get; set; }

    [JsonPropertyName("priority")]
    public string Priority { get; set; } = "MEDIUM";

    [JsonPropertyName("status")]
    public string Status { get; set; } = "PENDING";

    [JsonPropertyName("due_date")]
    public DateTime? DueDate { get; set; }

    [JsonPropertyName("created_at")]
    public DateTime CreatedAt { get; set; }
}